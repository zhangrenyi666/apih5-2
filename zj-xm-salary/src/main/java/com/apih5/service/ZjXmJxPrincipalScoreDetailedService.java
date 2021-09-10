package com.apih5.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxPrincipalScoreDetailed;

public interface ZjXmJxPrincipalScoreDetailedService {

	public ResponseEntity getZjXmJxPrincipalScoreDetailedListByCondition(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed);

	public ResponseEntity getZjXmJxPrincipalScoreDetailedDetails(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed);

	public ResponseEntity saveZjXmJxPrincipalScoreDetailed(ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed);

	public ResponseEntity updateZjXmJxPrincipalScoreDetailed(ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed);

	public ResponseEntity batchDeleteUpdateZjXmJxPrincipalScoreDetailed(
			List<ZjXmJxPrincipalScoreDetailed> zjXmJxPrincipalScoreDetailedList);

	public ResponseEntity getZjXmJxPrincipalScoreDetailedListByReviewer(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed);

	public ResponseEntity tempOrSubmitZjXmJxPrincipalScoreDetailedList(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed);

	public ResponseEntity getZjXmJxPrincipalScoreDetailedPrincipalExcel(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed);

	public void exportZjXmJxPrincipalScoreDetailedPrincipalExcel(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed, HttpServletResponse response);
}
