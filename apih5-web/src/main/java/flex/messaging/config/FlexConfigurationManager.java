/*************************************************************************
 *
 * ADOBE CONFIDENTIAL
 * __________________
 *
 *  [2002] - [2007] Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/
package flex.messaging.config;

import flex.messaging.LocalizedException;
import flex.messaging.util.Trace;
import flex.messaging.util.ClassUtil;

import javax.servlet.ServletConfig;
import java.io.File;

/**
 * Manages which ConfigurationParser implementation will be
 * used to read in the services configuration file and determines
 * where the configuration file is located.
 * <p>
 * The default location of the configuration file is
 * /WEB-INF/flex/services-config.xml, however this value can
 * be specified in a servlet init-param &quot;services.configuration.file&quot;
 * to the MessageBrokerServlet.
 * </p>
 * <p>
 * The ConfigurationParser implementation can also be specified in
 * a servlet init-param &quot;services.configuration.parser&quot; to
 * the MessageBrokerServlet.
 * </p>
 *
 * @author Peter Farland
 * @see ConfigurationParser
 * @exclude
 */
public class FlexConfigurationManager implements ConfigurationManager
{
    static final String DEFAULT_CONFIG_PATH = "/WEB-INF/flex/services-config.xml";

    protected String configurationPath = null;
    protected ConfigurationFileResolver configurationResolver = null;
    protected ConfigurationParser parser = null;

    public MessagingConfiguration getMessagingConfiguration(ServletConfig servletConfig)
    {
        MessagingConfiguration config = new MessagingConfiguration();

        if (servletConfig != null)
        {
            String serverInfo = servletConfig.getServletContext().getServerInfo();
            config.getSecuritySettings().setServerInfo(serverInfo);
        }

        verifyMinimumJavaVersion();

        parser = getConfigurationParser(servletConfig);

        if (parser == null)
        {
            // "Unable to create a parser to load messaging configuration."
            LocalizedException lme = new LocalizedException();
            lme.setMessage(10138);
            throw lme;
        }

        setupConfigurationPathAndResolver(servletConfig);
        parser.parse(configurationPath, configurationResolver, config);

        if (servletConfig != null)
        {
            config.getSystemSettings().setPaths(servletConfig.getServletContext());
        }

        return config;
    }

    public void reportTokens()
    {
        parser.reportTokens();
    }

    protected ConfigurationParser getConfigurationParser(ServletConfig servletConfig)
    {
        ConfigurationParser parser = null;
        Class parserClass = null;
        String className = null;

        // Check for Custom Parser Specification
        if (servletConfig != null)
        {
            String p = servletConfig.getInitParameter("services.configuration.parser");
            if (p != null)
            {
                className = p.trim();
                try
                {
                    parserClass = ClassUtil.createClass(className);
                    parser = (ConfigurationParser)parserClass.newInstance();
                }
                catch (Throwable t)
                {
                    if (Trace.config)
                    {
                        Trace.trace("Could not load configuration parser as: " + className);
                    }
                }
            }
        }

        // Always try Sun JRE 1.4 / Apache Xalan Based Implementation first to
        // avoid performance problems with Sun JRE 1.5 Based Implementation
        if (parser == null)
        {
            try
            {
                ClassUtil.createClass("org.apache.xpath.CachedXPathAPI");
                className = "flex.messaging.config.ApacheXPathServerConfigurationParser";
                parserClass = ClassUtil.createClass(className);
                parser = (ConfigurationParser)parserClass.newInstance();
            }
            catch (Throwable t)
            {
                if (Trace.config)
                {
                    Trace.trace("Could not load configuration parser as: " + className);
                }
            }
        }

        // Try Sun JRE 1.5 Based Implementation
        if (parser == null)
        {
            try
            {
                className = "flex.messaging.config.XPathServerConfigurationParser";
                parserClass = ClassUtil.createClass(className);
                // double-check, on some systems the above loads but the import classes don't
                ClassUtil.createClass("javax.xml.xpath.XPathExpressionException");

                parser = (ConfigurationParser)parserClass.newInstance();
            }
            catch (Throwable t)
            {
                if (Trace.config)
                {
                    Trace.trace("Could not load configuration parser as: " + className);
                }
            }
        }

        if (Trace.config && parser != null)
        {
            Trace.trace("Services Configuration Parser: " + parser.getClass().getName());
        }

        return parser;
    }

    /**
     * If no entry is specified in web.xml, assumed services-config.xml in the web application
     * If an entry is specified for windows starting with '/', it's assumed to be in the web application.
     * If an entry is specified for windows not starting with '\', it's assumed to be on the local file system.
     * If an entry is specified for non-windows starting with '/', we will first look in the web application
     *  then the the local file system.
     */
    protected void setupConfigurationPathAndResolver(ServletConfig servletConfig)
    {
        if (servletConfig != null)
        {
            String p = servletConfig.getInitParameter("services.configuration.file");
            if ((p == null) || (p.trim().length() == 0))
            {
                // no entry specified in web.xml, always use default and ServletResourceResolver
                configurationPath = DEFAULT_CONFIG_PATH;
                configurationResolver = new ServletResourceResolver(servletConfig.getServletContext());
            }
            else
            {
                // an entry was specified in web.xml,
                configurationPath = p.trim();

                // on windows, all paths starting with '/' should be available via the servlet resource resolver
                // on other systems, you're not sure so try the servlet resource loader first it but don't throw an error,
                //   after that try using LocalFileResolver
                boolean isWindows = File.separator.equals("\\");
                boolean isServletResource = isWindows && configurationPath.startsWith("/");
                if (isServletResource || !isWindows || !p.contains("WEB-INF"))
                {
                    ServletResourceResolver resolver = new ServletResourceResolver(servletConfig.getServletContext());
                    boolean available = resolver.isAvailable(configurationPath, isServletResource);
                    if (available)
                    {
                        // it's available via the servlet resource loader
                        configurationResolver = (ConfigurationFileResolver)resolver;
                    }
                    else
                    {
                        // it wasn't available via the servlet resource loader
                        configurationResolver = new LocalFileResolver(LocalFileResolver.SERVER);
                    }
                }
                else
                {
                    // it's windows but seems to be specified as a file
                    configurationResolver = new LocalFileResolver(LocalFileResolver.SERVER);
                }
            }
        }

        // no entry specified in web.xml
        else
        {
            configurationPath = DEFAULT_CONFIG_PATH;
            configurationResolver = new ServletResourceResolver(servletConfig.getServletContext());
        }


    }

    protected void verifyMinimumJavaVersion() throws ConfigurationException
    {
        try
        {
            boolean minimum = false;
            String version = System.getProperty("java.version");
            String vendor = System.getProperty("java.vendor");

            version = version.replace('.', ':');
            version = version.replace('_', ':');
            String[] split = version.split(":");

            int first = Integer.parseInt(split[0]);
            if (first > 1)
            {
                minimum = true;
            }
            else if (first == 1)
            {
                int second = Integer.parseInt(split[1]);
                if (second > 4)
                {
                    minimum = true;
                }
                else  if (second == 4)
                {
                    int third = Integer.parseInt(split[2]);
                    if (third > 2)
                    {
                        minimum = true;
                    }
                    else if (third == 2)
                    {
                        if ((vendor != null) && (vendor.indexOf("Sun") != -1))
                        {
                            // test at least 1.4.2_06 on Sun
                            int fourth = Integer.parseInt(split[3]);
                            if (fourth >= 6)
                            {
                                minimum = true;
                            }
                        }
                        else
                        {
                            // test at least 1.4.2 on non-Sun
                            minimum = true;
                        }
                    }
                }
            }

            if (!minimum)
            {
                ConfigurationException cx = new ConfigurationException();

                if ((vendor != null) && (vendor.indexOf("Sun") != -1))
                {
                    // The minimum required Java version was not found. Please install JDK 1.4.2_06 or above. Current version is XX.
                    cx.setMessage(10139, new Object[] { System.getProperty("java.version")});
                }
                else
                {
                    // The minimum required Java version was not found. Please install JDK 1.4.2 or above. Current version is XX.
                    cx.setMessage(10140, new Object[] { System.getProperty("java.version")});
                }

                throw cx;
            }
        }
        catch (Throwable t)
        {
            if (t instanceof ConfigurationException)
            {
                throw ((ConfigurationException)t);
            }
            else
            {
                if (Trace.config)
                {
                    Trace.trace("Could not verified required java version. version=" + System.getProperty("java.version"));
                }
            }
        }
    }

}
