import LoginPage from "./login";
import Home from "./home";
import Supplier from './Supplier';
import SupplierDetail from './Supplier/SupplierDetail';
import Scheme from './Scheme';
import SchemeDetail from './Scheme/SchemeDetail';
import Organization from './Organization';
import OrganizationDetail from './Organization/OrganizationDetail';
import Inventory from './Inventory';
import InventoryDetail from './Inventory/InventoryDetail';
import Personnel from './Personnel';
import PersonnelDetail from './Personnel/PersonnelDetail';
import Courseware from './Courseware';
import CoursewareDetail from './Courseware/CoursewareDetail';
import MixProportion from './MixProportion';
import MixProportionDetail from './MixProportion/MixProportionDetail';
import Material from './Material';
import MaterialDetail from './Material/MaterialDetail';
import Materials from './Materials';
import MaterialsDetail from './Materials/MaterialsDetail';
import Subpackage from './Subpackage';
import SubpackageList from './Subpackage/SubpackageList';
import SubpackageDetail from './Subpackage/SubpackageDetail';
import Temp from './Temp';
import TempDetail from './Temp/TempDetail';
const pageComs = {
    /* 共通页面start */
    Login: {
        mustLogin: false,
        Com: LoginPage
    },
    Home: {
        mustLogin: true,
        Com: Home
    },
    Supplier: {
        mustLogin: true,
        Com: Supplier
    },
    SupplierDetail: {
        mustLogin: true,
        Com: SupplierDetail
    },
    Scheme: {
        mustLogin: true,
        Com: Scheme
    },
    SchemeDetail: {
        mustLogin: true,
        Com: SchemeDetail
    },
    Organization: {
        mustLogin: true,
        Com: Organization
    },
    OrganizationDetail: {
        mustLogin: true,
        Com: OrganizationDetail
    },
    Inventory: {
        mustLogin: true,
        Com: Inventory
    },
    InventoryDetail: {
        mustLogin: true,
        Com: InventoryDetail
    },
    Personnel: {
        mustLogin: true,
        Com: Personnel
    },
    PersonnelDetail: {
        mustLogin: true,
        Com: PersonnelDetail
    },
    Courseware: {
        mustLogin: true,
        Com: Courseware
    },
    CoursewareDetail: {
        mustLogin: true,
        Com: CoursewareDetail
    },
    MixProportion: {
        mustLogin: true,
        Com: MixProportion
    },
    MixProportionDetail: {
        mustLogin: true,
        Com: MixProportionDetail
    },
    Material: {
        mustLogin: true,
        Com: Material
    },
    MaterialDetail: {
        mustLogin: true,
        Com: MaterialDetail
    },
    Materials: {
        mustLogin: true,
        Com: Materials
    },
    MaterialsDetail: {
        mustLogin: true,
        Com: MaterialsDetail
    },
    Subpackage: {
        mustLogin: true,
        Com: Subpackage
    },
    SubpackageList: {
        mustLogin: true,
        Com: SubpackageList
    },
    SubpackageDetail: {
        mustLogin: true,
        Com: SubpackageDetail
    },
    Temp: {
        mustLogin: true,
        Com: Temp
    },
    TempDetail: {
        mustLogin: true,
        Com: TempDetail
    },
};
const routerInfo = [
    {
        label: "首页",
        defaultPath: "HomePage",
        pathName: "HomePage",
        comKey: "HomePage",
    },
    {
        label: "供应商列表",
        pathName: "Supplier",
        defaultPath: "Supplier",
        comKey: "Supplier",
    },     
    {
        label: "供应商详情",
        defaultPath: "SupplierDetail/0",
        pathName: "SupplierDetail/:supplierId",
        comKey: "SupplierDetail",
    },
    {
        label: "技术质量成果列表",
        pathName: "Scheme",
        defaultPath: "Scheme",
        comKey: "Scheme",
    },     
    {
        label: "技术质量成果详情",
        defaultPath: "SchemeDetail/0",
        pathName: "SchemeDetail/:schemeId",
        comKey: "SchemeDetail",
    },
    {
        label: "机构管理列表",
        pathName: "Organization",
        defaultPath: "Organization",
        comKey: "Organization",
    },     
    {
        label: "机构管理详情",
        defaultPath: "OrganizationDetail/0",
        pathName: "OrganizationDetail/:organizationId",
        comKey: "OrganizationDetail",
    },
    {
        label: "清单管理列表",
        pathName: "Inventory",
        defaultPath: "Inventory",
        comKey: "Inventory",
    },     
    {
        label: "清单管理详情",
        defaultPath: "InventoryDetail/0",
        pathName: "InventoryDetail/:inventoryId",
        comKey: "InventoryDetail",
    },
    {
        label: "人员库管理列表",
        pathName: "Personnel",
        defaultPath: "Personnel",
        comKey: "Personnel",
    },     
    {
        label: "人员库管理详情",
        defaultPath: "PersonnelDetail/0",
        pathName: "PersonnelDetail/:personnelId",
        comKey: "PersonnelDetail",
    },
    {
        label: "课件管理列表",
        pathName: "Courseware",
        defaultPath: "Courseware",
        comKey: "Courseware",
    },     
    {
        label: "课件库管理详情",
        defaultPath: "CoursewareDetail/0",
        pathName: "CoursewareDetail/:coursewareId",
        comKey: "CoursewareDetail",
    },
    {
        label: "配合比列表",
        pathName: "MixProportion",
        defaultPath: "MixProportion",
        comKey: "MixProportion",
    },     
    {
        label: "配合比详情",
        defaultPath: "MixProportionDetail/0",
        pathName: "MixProportionDetail/:mixProportionId",
        comKey: "MixProportionDetail",
    },
    {
        label: "材料消耗定额列表",
        pathName: "Material",
        defaultPath: "Material",
        comKey: "Material",
    },     
    {
        label: "材料消耗定额详情",
        defaultPath: "MaterialDetail/0",
        pathName: "MaterialDetail/:materialQuotaId",
        comKey: "MaterialDetail",
    },
    {
        label: "物资限价列表",
        pathName: "Materials",
        defaultPath: "Materials",
        comKey: "Materials",
    },     
    {
        label: "物资限价详情",
        defaultPath: "MaterialsDetail/0",
        pathName: "MaterialsDetail/:materialPriceId",
        comKey: "MaterialsDetail",
    },
    {
        label: "分包限价列表",
        pathName: "Subpackage",
        defaultPath: "Subpackage",
        comKey: "Subpackage",
    }, 
    {
        label: "章节详情列表",
        defaultPath: "SubpackageList/0",
        pathName: "SubpackageList/:subpackageMainId",
        comKey: "SubpackageList",
    },     
    {
        label: "分包限价详情",
        defaultPath: "SubpackageDetail/0",
        pathName: "SubpackageDetail/:subpackageDetailId",
        comKey: "SubpackageDetail",
    },
    {
        label: "临建限价列表",
        pathName: "Temp",
        defaultPath: "Temp",
        comKey: "Temp",
    },     
    {
        label: "临建限价详情",
        defaultPath: "TempDetail/0",
        pathName: "TempDetail/:tempPriceId",
        comKey: "TempDetail",
    },
];
const reducers = {};
const actions = {};
const sagas = [];
const MyViews = {
    pageComs,
    routerInfo,
    reducers,
    sagas,
    actions
};
export default MyViews;
