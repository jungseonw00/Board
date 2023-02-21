package com.study.pmis.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.IllegalCharsetNameException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gpki.gpkiapi.GpkiApi;
import com.gpki.gpkiapi.cert.X509Certificate;
import com.gpki.gpkiapi.cms.SignedAndEnvelopedData;
import com.gpki.gpkiapi.crypto.PrivateKey;
import com.gpki.gpkiapi.exception.GpkiApiException;
import com.gpki.gpkiapi.storage.Disk;
import com.gpki.gpkiapi.util.Base64;
import com.gpki.gpkiapi.util.Ldap;

import kr.go.pmis.UniComnContents;
import kr.go.pmis.alib.core.ApiResult;
import kr.go.pmis.alib.core.Link;
import kr.go.pmis.alib.exception.BizUserException;
import kr.go.pmis.comn.service.UniKoreaServiceImpl;
import kr.go.pmis.egov.com.service.EgovProperties;
import kr.go.pmis.egov.com.util.EgovStringUtil;
import kr.go.pmis.schedmgmt.confmProcess.dao.ConfmProcessManageDao;
import kr.go.pmis.schedmgmt.confmProcess.service.ConfmProcessManageService;
import kr.go.pmis.schedmgmt.confmProcess.vo.ConfmProcessManageVo;
import kr.go.pmis.schedmgmt.lnbns.vo.LnbnsSchdulManageVo;

@Service
public class ConfmProcessManageServiceImpl extends UniKoreaServiceImpl implements ConfmProcessManageService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ConfmProcessManageDao confmProcessManageDao;

	/**
	 * <pre>
	 *  
	 * Method 명 : selectListConfmProcessTrget
	 * Method 설명 : 승인 처리 대상 건 조회
	 * </pre>
	 * 
	 * @param confmProcessManageVo
	 * @return : ApiResult<List<ConfmProcessManageVo>>
	 * @since : 2020. 2. 5.
	 */
	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectListConfmProcessTrget(
			ConfmProcessManageVo confmProcessManageVo) {

		PageHelper.startPage(confmProcessManageVo.getCurPage(), confmProcessManageVo.getPageSize());

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectListConfmProcessTrget(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		PageInfo<ConfmProcessManageVo> pageInfo = new PageInfo<>(list);

		apiResult.setLinks(new Link(pageInfo));
		apiResult.setData(list);
		return apiResult;
	}
	
	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectFrequentDwld(ConfmProcessManageVo confmProcessManageVo) {

		PageHelper.startPage(confmProcessManageVo.getCurPage(), confmProcessManageVo.getPageSize());

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectFrequentDwld(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		apiResult.setData(list);
		
		return apiResult;
	}

	/**
	 * <pre>
	 * Method 명 : selectListConfmProcessTrgetExcel
	 * Method 설명 : 승인 처리 대상 건 엑셀 다운
	 * </pre>
	 * 
	 * @param confmProcessManageVo
	 * @return : ApiResult<List<ConfmProcessManageVo>>
	 * @since : 2020. 2. 5.
	 */
	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectListConfmProcessTrgetExcel(
			ConfmProcessManageVo confmProcessManageVo) {

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectListConfmProcessTrget(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		confmProcessManageDao.insertFileDwldLog(confmProcessManageVo);
		confmProcessManageDao.insertFileDwldDtlLogByConfmNo(confmProcessManageVo);

		apiResult.setData(list);
		return apiResult;
	}

	/**
	 * <pre>
	 * Method 명 : confmProcessList
	 * Method 설명 : 승인 처리 (대상 목록)
	 * </pre>
	 * 
	 * @param confmProcessManageVo
	 * @return : ApiResult<ConfmProcessManageVo>
	 * @since : 2020. 2. 6.
	 */
	@Override
	public ApiResult<ConfmProcessManageVo> confmProcessList(ConfmProcessManageVo confmProcessManageVo) {

		ApiResult<ConfmProcessManageVo> apiResult = new ApiResult<ConfmProcessManageVo>();

		for (ConfmProcessManageVo srcVo : confmProcessManageVo.getList()) {

			String confmNo = confmProcessManageDao.getConfmNo();
			srcVo.setConfmNo(confmNo);
			srcVo.setNewProcessSttus(confmProcessManageVo.getNewProcessSttus());
			srcVo.setProcessPrvonsh(confmProcessManageVo.getProcessPrvonsh());
			confmProcess(srcVo);

		}

		apiResult.setMessage(Integer.toString(confmProcessManageVo.getList().size()) + "건 처리되었습니다.");

		return apiResult;
	}

	/**
	 * <pre>
	 * Method 명 : confmProcess
	 * Method 설명 : 승인 처리 (대상 단건)
	 * </pre>
	 * 
	 * @param confmProcessManageVo
	 * @return : ConfmProcessManageVo
	 * @since : 2020. 2. 6.
	 */
	private ConfmProcessManageVo confmProcess(ConfmProcessManageVo inConfmProcessManageVo) {

		ConfmProcessManageVo confmProcessManageVo = inConfmProcessManageVo;

		confmProcessManageDao.insertReqstSanctnProcess(confmProcessManageVo);

		confmProcessManageDao.updateReqstHnMatterToReqstSttusCode(confmProcessManageVo);

		confmProcessManageDao.insertReqstHnMatterHist(confmProcessManageVo);

		confmProcessManageDao.updateReqstInfoToProcessSttus(confmProcessManageVo);

		confmProcessManageDao.insertReqstProcessHist(confmProcessManageVo);

		// 승인 처리시 sms발송, 기관견학은 빼기로 2020.05.13 채유진 주무관 요청
		if (inConfmProcessManageVo.getLnbnsSeCode().equals("01")) {
			if (confmProcessManageVo.getNewProcessSttus().equals("2")) {
				confmProcessManageVo.setSj("확정알림");
				confmProcessManageVo.setCn("확정 처리 되었습니다.");

				// confmProcessManageDao.insertDcsmMssageTrnsmisInfo(confmProcessManageVo);

				// confmProcessManageDao.insertDcsmMssageTrnsmisInfo2(confmProcessManageVo);
				// confmProcessManageDao.insertDcsmMssageTrnsmisInfo3(confmProcessManageVo);
				/*셔틀버스 관련 내용 추가 */
				//confmProcessManageDao.insertDcsmMssageTrnsmisInfo4(confmProcessManageVo);
				confmProcessManageDao.insertDcsmMssageTrnsmisInfo5(confmProcessManageVo);

			} else {
				confmProcessManageVo.setSj("불허알림");
				confmProcessManageVo.setCn(" '" + confmProcessManageVo.getProcessPrvonsh() + "' 사유로 불허 처리 되었습니다.");
				confmProcessManageDao.insertNnpmsnMssageTrnsmisInfo(confmProcessManageVo);
			}

		}

		return confmProcessManageVo;
	}

	/**
	 * <pre>
	 * Method 명 : selectListLnbnsNmprOutpt
	 * Method 설명 : 견학 인원 목록 출력
	 * </pre>
	 * 
	 * @param confmProcessManageVo
	 * @return : ApiResult<List<ConfmProcessManageVo>>
	 * @since : 2020. 2. 5.
	 */
	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectListLnbnsNmprOutpt(ConfmProcessManageVo confmProcessManageVo) {

		PageHelper.startPage(confmProcessManageVo.getCurPage(), confmProcessManageVo.getPageSize());

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectListLnbnsNmprOutpt(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();
		
		PageInfo<ConfmProcessManageVo> pageInfo = new PageInfo<>(list);

		apiResult.setLinks(new Link(pageInfo));
		apiResult.setData(list);
		return apiResult;
	}

	/**
	 * <pre>
	 * Method 명 : selectListLnbnsNmprOutptExcel
	 * Method 설명 : 견학 인원 목록 출력(엑셀)
	 * </pre>
	 * 
	 * @param confmProcessManageVo
	 * @return : ApiResult<List<ConfmProcessManageVo>>
	 * @since : 2020. 2. 5.
	 */
	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectListLnbnsNmprOutptExcel(
			ConfmProcessManageVo confmProcessManageVo) {

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectListLnbnsNmprOutpt(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		apiResult.setData(list);

		confmProcessManageDao.insertFileDwldLog(confmProcessManageVo);
		confmProcessManageDao.insertFileDwldDtlLogByConfmNo(confmProcessManageVo);

		return apiResult;
	}

	/**
	 * <pre>
	 * Method 명 : insertFileDwldLog
	 * Method 설명 : 파일 다운로드 입력
	 * </pre>
	 * 
	 * @param confmProcessManageVo
	 * @return : void
	 * @since : 2020. 2. 5.
	 */
	@Override
	public void insertFileDwldLog(ConfmProcessManageVo confmProcessManageVo) {

		confmProcessManageDao.insertFileDwldLog(confmProcessManageVo);
		confmProcessManageDao.insertFileDwldDtlLogByConfmNo(confmProcessManageVo);

	}

	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectListExmplLnbns(ConfmProcessManageVo confmProcessManageVo) {

		PageHelper.startPage(confmProcessManageVo.getCurPage(), confmProcessManageVo.getPageSize());

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectListExmplLnbns(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		PageInfo<ConfmProcessManageVo> pageInfo = new PageInfo<>(list);

		apiResult.setLinks(new Link(pageInfo));
		apiResult.setData(list);
		return apiResult;
	}

	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectListExmplLnbnsDrwt() {

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectListExmplLnbnsDrwt();

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		apiResult.setData(list);
		return apiResult;
	}

	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectListExmplLnbnsDrwt2() {

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectListExmplLnbnsDrwt2();

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		apiResult.setData(list);
		return apiResult;
	}

	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectListLnbnsNmprUpdate(ConfmProcessManageVo confmProcessManageVo) {

		PageHelper.startPage(confmProcessManageVo.getCurPage(), confmProcessManageVo.getPageSize());

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectListLnbnsNmprUpdate(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		PageInfo<ConfmProcessManageVo> pageInfo = new PageInfo<>(list);

		apiResult.setLinks(new Link(pageInfo));
		apiResult.setData(list);
		return apiResult;
	}

	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectListLnbnsNmprDetailList(
			ConfmProcessManageVo confmProcessManageVo) {

		List<ConfmProcessManageVo> list = confmProcessManageDao.LnbnsNmprDetailList(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		apiResult.setData(list);
		return apiResult;
	}

	@Override
	public ApiResult<Integer> LnbnsUpdate(List<ConfmProcessManageVo> confmProcessManageVo) {

		ApiResult<Integer> apiResult = new ApiResult<>();
		List<ConfmProcessManageVo> list = confmProcessManageVo;
		for (ConfmProcessManageVo vo : list) {
			confmProcessManageDao.insertReqstProcessHist(vo); // 견학 신청 건 이력 남기기
			confmProcessManageDao.insertReqstHnMatterHist(vo); // 견학 신청인원 이력 남기기
			int rt = confmProcessManageDao.LnbnsUpdate(vo);
			if (rt > 0) {

				apiResult.setStatus(UniComnContents.API_RT_SUCCESS);
				apiResult.setMessage("견학취소가 완료되었습니다.");
				return apiResult;
			} else {
				apiResult.setStatus(UniComnContents.API_RT_FAIL);
				apiResult.setMessage("견학취소를 실패하였습니다.");
				return apiResult;

			}
		}
		return apiResult;
	}

	@Override
	public ApiResult<Integer> LnbnsNmprDelete(List<ConfmProcessManageVo> confmProcessManageVo) {

		ApiResult<Integer> apiResult = new ApiResult<>();
		List<ConfmProcessManageVo> list = confmProcessManageVo;

		for (ConfmProcessManageVo vo : list) {
			confmProcessManageDao.insertReqstProcessHist(vo);  // 견학 신청 건 이력 남기기
			confmProcessManageDao.insertReqstHnMatterHist(vo); // 견학 신청인원 이력 남기기
			int rt = confmProcessManageDao.LnbnsNmprDelete(vo);
			if (rt > 0) {
				confmProcessManageDao.LnbnsNmprCoUpdate(vo);
				apiResult.setStatus(UniComnContents.API_RT_SUCCESS);
				apiResult.setMessage("선택한 견학인원 정보가 취소되었습니다.");
			} else {
				apiResult.setStatus(UniComnContents.API_RT_FAIL);
				apiResult.setMessage("취소를 실패하였습니다.");
			}
		}
		return apiResult;
	}

	@Override
	public ApiResult<Integer> LnbnsNmprUpdate(List<ConfmProcessManageVo> confmProcessManageVo) {
		ApiResult<Integer> apiResult = new ApiResult<>();
		List<ConfmProcessManageVo> list = confmProcessManageVo;
		for (ConfmProcessManageVo vo : list) {

			if (vo.getStatus().equals("I")) {
				confmProcessManageDao.insertReqstProcessHist(vo); // 견학 신청 건 이력 남기기
				confmProcessManageDao.insertReqstHnMatterHist(vo); // 견학 신청인원 이력 남기기
				int rt = confmProcessManageDao.LnbnsNmprInsert(vo);
				if (rt > 0) {
					confmProcessManageDao.LnbnsNmprCoUpdate(vo);
					apiResult.setStatus(UniComnContents.API_RT_SUCCESS);
					apiResult.setMessage("견학 인원이 추가 되었습니다.");

				} else {
					apiResult.setStatus(UniComnContents.API_RT_FAIL);
					apiResult.setMessage("견학 인원 추가 실패");

				}

			}

			if (vo.getStatus().equals("U")) {

				confmProcessManageDao.insertReqstHnMatterHist(vo); // 견학 신청인원 이력 남기기
				int rt = confmProcessManageDao.LnbnsNmprUpdate(vo);
				if (rt > 0) {

					apiResult.setStatus(UniComnContents.API_RT_SUCCESS);
					apiResult.setMessage("선택한 견학인원 정보가 수정되었습니다.");

				} else {
					apiResult.setStatus(UniComnContents.API_RT_FAIL);
					apiResult.setMessage("수정을 실패하였습니다.");

				}
			}
		}
		return apiResult;
	}

	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectLnbnsVhcleList(ConfmProcessManageVo confmProcessManageVo) {

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectLnbnsVhcleList(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		apiResult.setData(list);
		return apiResult;
	}

	private Ldap ldap = null;
	private byte[] bCertInfo = null;
	// GPKI 인증 연계
	private final String PRIVATE_GPKI_CONF_PATH = EgovProperties.getProperty("Globals.gpkiapi.ConfFilePath"); // GPKI
																												// 설정파일
																												// 경로
	private final String PRIVATE_SIGN_CERT_PATH = EgovProperties.getProperty("Globals.SignCertFilePathName"); // "SVRXXXXXXsig.cer";
																												// //이용기관
																												// 인증서
																												// 파일
	private final String PRIVATE_SIGN_KEY_PATH = EgovProperties.getProperty("Globals.SignPrivateKeyFilePathName"); // "SVRXXXXXXsig.key";
																													// //이용기관
																													// 인증서
																													// 파일
	private final String PRIVATE_KM_CERT_PATH = EgovProperties.getProperty("Globals.CertFilePathName"); // "SVRXXXXXXenv.cer";
																										// //이용기관 인증서 파일
	private final String PRIVATE_KM_KEY_PATH = EgovProperties.getProperty("Globals.PrivateKeyFilePathName"); // "SVRXXXXXXenv.key";
																												// //이용기관
																												// 인증서
																												// 파일
	private final String PRIVATE_PASSWD = EgovProperties.getProperty("Globals.PrivateKeyPasswd"); // 이용기관 인증서 비밀번호
	private final String PRIVATE_CD = EgovProperties.getProperty("Globals.resident_cd"); // 이용기관 코드
	private final String PRIVATE_CN = EgovProperties.getProperty("Globals.resident_cn"); // 서버인증서 CN
	private final String PRIVATE_SERVICE_CD = "01"; // 서비스 구분코드 (실명확인은 '01'로 고정)
	private final String SERVICE_ENVR = EgovProperties.getProperty("Globals.serviceEnvr");
	private X509Certificate x509Cert = null;
	private Base64 b64 = null;

	private URL url = null;
	private URLConnection urlconn = null;
	private BufferedReader in = null;
	private PrintWriter sendParam = null;
	private X509Certificate pri_x509cert = null;
	private PrivateKey privateKey = null;
	private SignedAndEnvelopedData signEnData = null;
	{
		try {
			if (SERVICE_ENVR.equals("REAL")) {
				b64 = new Base64();
				log.info("bCertInfo - " + bCertInfo);
				if (bCertInfo == null || bCertInfo.length < 1) {
					try {
						log.info("PRIVATE_GPKI_CONF_PATH - " + PRIVATE_GPKI_CONF_PATH);
						GpkiApi.init(PRIVATE_GPKI_CONF_PATH);
					} catch (GpkiApiException e) {
						log.error("[실명인증 오류] - 2. GpkiApi 초기화 오류 : " + e.getMessage());
						throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
					}
					try {
						ldap = new Ldap();
						ldap.setLdap("ldap.gcc.go.kr", 389);
						// 안전행정부 운영서버 인증서정보 획득(*이 부분은 수정하지 말아야 합니다)
						bCertInfo = ldap.getData(Ldap.DATA_TYPE_KM_CERT,
								"cn=SVR1311361003,ou=Group of Server,o=Government of Korea,c=KR");
					} catch (GpkiApiException e) {
						log.error("[실명인증 오류] - 3. ldap 초기화 오류 : " + e.getMessage());
						throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
					}

				}
				if (x509Cert == null) {
					x509Cert = new X509Certificate(bCertInfo);
				}

				if (signEnData == null) {
					// UTF-8로 인코딩된 원본데이터를 이용기관의 서버인증서를 이용하여 암호화 및 서명
					signEnData = new SignedAndEnvelopedData();
					if (pri_x509cert == null) {
						log.info("pri_x509cert 다시 읽기");
						pri_x509cert = Disk.readCert(PRIVATE_SIGN_CERT_PATH);
					}
					if (privateKey == null) {
						log.info("privateKey 다시 읽기");
						privateKey = Disk.readPriKey(PRIVATE_SIGN_KEY_PATH, PRIVATE_PASSWD);
					}

					signEnData.setMyCert(pri_x509cert, privateKey);

				}
			}

		} catch (Exception e) {
			log.error("실명인증 모듈 초기화 중 오류 발생 !!! ");
		}

	}

	public boolean idihCheck(String name, String jumin) throws BizUserException {

		boolean boolRt = true;// 결과 전달
		if (SERVICE_ENVR.equals("REAL")) {
			// 안전행정부 실명확인 운영서버 (인터넷망으로 접속시)
			final String URL_ADDRESS = "http://rcen.egov.go.kr/servlets/mopas/jumin/main/MopasJuminCheck";
			// 안전행정부 실명확인 운영서버 (행정망으로 접속시)
			// final String URL_ADDRESS
			// ="http://10.51.30.46:7020/servlets/mopas/jumin/main/MopasJuminCheck";

			SignedAndEnvelopedData signDeData = null;

			byte[] bOrgSendData = null; // 원본텍스트를 바이트로 저장하기 위한 변수
			byte[] bEncData = null; // 암호화 된 결과를 저장하기 위한 변수
			String sEncData = ""; // URL전송을 위해 텍스트로 저장하기 위한 변수
			String sResultData = ""; // 안전행정부서버로 부터 Response받은 데이터를 저장하는 변수

			String sResultDecData = ""; // 수신전문
			String sResidentResult = ""; // 실명확인결과

			// 입력데이터형식 (암복호화 하기전 원본 데이터형식)
			// 이용기관코드(10)|서버인증서cn번호('SVR'을 제외한 숫자만)(10)|01(2)|주민등록번호(13)|성명(가변, 최대 38Byte)
			String sData = PRIVATE_CD + "|" + PRIVATE_CN + "|" + PRIVATE_SERVICE_CD + "|" + jumin + "|" + name;

			if (log.isDebugEnabled())
				log.debug("실명 인증 명 :[] ", name);

			if (name == null)
				throw new BizUserException("실명인증 이름이 null입니다 ");
			if (jumin == null)
				throw new BizUserException("실명인증 '" + name + "'의 주민번호 값이  null입니다 ");

			if (!EgovStringUtil.isEmpty(SERVICE_ENVR) && !SERVICE_ENVR.equals("TEST")) {

				try {
					// log.debug("Org sData >>"+sData);
					// 원본데이터를 UTF-8형식으로 인코딩 (KSC5601외 한글처리를 위해)
					sData = URLEncoder.encode(sData, "UTF-8");
					// log.debug("After URL Encoding with UTF-8 Data >>"+sData);
					bOrgSendData = sData.getBytes(); // 입력받은 원본텍스트를 바이트 변수에 저장
				} catch (UnsupportedEncodingException e) {
					log.error("[실명인증 오류] - 1. 송신전문 UTF8 인코딩 오류 : " + e.getMessage());
					throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
				} catch (IllegalCharsetNameException e) {
					log.error("[실명인증 오류] - 1. 송신전문 UTF8 인코딩 오류 : " + e.getMessage());
					throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
				}

				try {

					bEncData = signEnData.generate(x509Cert, bOrgSendData);

					// 암호화된 데이터를 base64 인코딩으로 String변환 하여 저장
					sEncData = b64.encode(bEncData);
					// log.debug("Base64 Encoding ["+sEncData+"]");

					// 안전행정부 실명확인 서비스 호출 및 데이터 전송
					url = new URL(URL_ADDRESS);
					urlconn = url.openConnection();
					urlconn.setDoOutput(true);

					sendParam = new PrintWriter(urlconn.getOutputStream());
					sendParam.print("data=" + URLEncoder.encode(sEncData, "UTF-8"));
					sendParam.flush();
					// sendParam.close();

					// 안전행정부 실명확인 서비스로부터 응답받은 데이터 처리
					in = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
					sResultData = in.readLine();
					if (sResultData != null) {
						byte[] bResultData = b64.decode(sResultData);
						signDeData = new SignedAndEnvelopedData();
						signDeData.setMyCert(Disk.readCert(PRIVATE_KM_CERT_PATH),
								Disk.readPriKey(PRIVATE_KM_KEY_PATH, PRIVATE_PASSWD));
						byte[] bDeData = signDeData.process(bResultData);
						sResultDecData = new String(bDeData, 0, bDeData.length);
						// log.debug("Before URLDecoder Result [" +sResultDecData +"]");
						sResultDecData = URLDecoder.decode(sResultDecData, "UTF-8");
						// log.debug("Result [" +sResultDecData +"]");
					} else {
						log.error("[실명인증 오류] - 3. 수신전문 오류 : sResultData is Null!!!!!!!!!!!!!!!");
						throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
					}
				} catch (GpkiApiException e) {
					log.error("[실명인증 오류] - 4. 통신 오류 : " + e.getMessage());
					throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
				} catch (UnsupportedEncodingException e) {
					log.error("[실명인증 오류] - 1. 송신전문 UTF8 인코딩 오류 : " + e.getMessage());
					throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
				} catch (IllegalCharsetNameException e) {
					log.error("[실명인증 오류] - 1. 송신전문 UTF8 인코딩 오류 : " + e.getMessage());
					throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
				} catch (IllegalArgumentException e) {
					log.error("[실명인증 오류] - 1. 송신전문 UTF8 인코딩 오류 : " + e.getMessage());
					throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
				} catch (MalformedURLException e) {

					log.error("[실명인증 오류] - 1. 송신전문 UTF8 인코딩 오류 : " + e.getMessage());
					throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
				} catch (IOException e) {
					log.error("[실명인증 오류] - 1. 송신전문 UTF8 인코딩 오류 : " + e.getMessage());
					throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
				} finally {
					try {
						if (sendParam != null)
							sendParam.close();
						if (in != null)
							in.close();

					} catch (IOException iex) {
						log.error("CLOSE 중 에러 발생: []", iex.getMessage());
					}
				}

				// 수신전문 :
				// 이용기관코드(10)|서버인증서cn번호(10)|서비스구분코드(2)|주민등록번호(13)|성명(가변)|실명확인결과(1)|서비스고유키(가변)
				// 실명확인결과 : 1(성공), 2(주민등록번호 오류), 3(성명 오류), 4(사망자)
				if (!"".equals(sResultDecData)) {
					sResidentResult = sResultDecData.split("[|]")[5];
				}

				if (sResidentResult != null && !"".equals(sResidentResult)) {
					if ("1".equals(sResidentResult)) {
						log.info("[실명인증 완료] - 5. 결과값 장상(sResidentResult) : " + sResidentResult);
						log.info("[실명인증 완료] - 5. 결과값 장상(name) : " + name);
						return true;
					} else if ("2".equals(sResidentResult) || "3".equals(sResidentResult)
							|| "4".equals(sResidentResult)) {
						log.info("[실명인증 입력오류] - 5. 통과못함 (sResidentResult) : " + sResidentResult);
						log.info("[실명인증 입력오류] - 5. 통과못함 (name) : " + name);
						return false;
					} else {
						log.error("[실명인증 오류] - 5. 결과값 오류(sResultDecData) : " + sResultDecData);
						throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
					}
				} else {
					log.error("[실명인증 오류] - 6. 결과값 오류 : sResidentResult is Null!!!!!!!!! ");
					log.error("[실명인증 오류] - 수신전문(sResultData) : " + sResultData);
					throw new BizUserException("실명확인 서비스에 문제가 있습니다.\n판문점 견학 센터에 문의하여 주십시오.( 1588-8889 )");
				}
			}
		}

		return boolRt;
	}

	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectListLnbnsExtendTime(ConfmProcessManageVo confmProcessManageVo) {

		PageHelper.startPage(confmProcessManageVo.getCurPage(), confmProcessManageVo.getPageSize());

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectListLnbnsExtendTime(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		PageInfo<ConfmProcessManageVo> pageInfo = new PageInfo<>(list);

		apiResult.setLinks(new Link(pageInfo));
		apiResult.setData(list);
		return apiResult;
	}

	@Override
	public ApiResult<Integer> updateLnbnsExtendTime(List<ConfmProcessManageVo> confmProcessManageVo) {

		ApiResult<Integer> apiResult = new ApiResult<>();
		List<ConfmProcessManageVo> list = confmProcessManageVo;
		for (ConfmProcessManageVo vo : list) {
			int rt = confmProcessManageDao.updateLnbnsExtendTime(vo);
			if (rt > 0) {
				apiResult.setStatus(UniComnContents.API_RT_SUCCESS);
				apiResult.setMessage("선택한 인원의 유효기간이 연장되었습니다.");
			} else {
				apiResult.setStatus(UniComnContents.API_RT_FAIL);
				apiResult.setMessage("연장에 실패하였습니다.");
			}
		}
		return apiResult;
	}

	@Override
	public ApiResult<List<LnbnsSchdulManageVo>> selectListLnbns(LnbnsSchdulManageVo lnbnsSchdulManageVo) {

		ApiResult<List<LnbnsSchdulManageVo>> result = new ApiResult<>();

		List<LnbnsSchdulManageVo> list = confmProcessManageDao.selectListLnbns(lnbnsSchdulManageVo);

		result.setData(list);
		return result;
	}

	@Override
	public ApiResult<Integer> LnbnsNmprInsert(List<ConfmProcessManageVo> confmProcessManageVo) {
		ApiResult<Integer> apiResult = new ApiResult<>();
		String reqstId = "";
		int insetChk = 0;
		reqstId = confmProcessManageDao.getReqstId(confmProcessManageVo);

		List<ConfmProcessManageVo> list = confmProcessManageVo;
		for (ConfmProcessManageVo vo : list) {
			vo.setReqstId(reqstId);
			if (vo.getStatus().equals("I")) {
				confmProcessManageDao.insertReqstProcessHist(vo); // 견학 신청 건 이력 남기기
				confmProcessManageDao.insertReqstHnMatterHist(vo); // 견학 신청인원 이력 남기기
				int rt = confmProcessManageDao.LnbnsNmprInsert(vo);
				insetChk++;
				if (rt > 0) {
					System.out.println("====================insetChk==========================");
					System.out.println(insetChk);
					System.out.println("====================listSize==========================");
					System.out.println(list.size());
					if (insetChk == list.size()) {

						confmProcessManageDao.LnbnsInfoInsert(vo);
					}
					apiResult.setStatus(UniComnContents.API_RT_SUCCESS);
					apiResult.setMessage("견학신청 성공");

				} else {
					apiResult.setStatus(UniComnContents.API_RT_FAIL);
					apiResult.setMessage("견학신청 실패");

				}

			}

		}
		return apiResult;
	}

	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectListPriorLnbns(ConfmProcessManageVo confmProcessManageVo) {

		PageHelper.startPage(confmProcessManageVo.getCurPage(), confmProcessManageVo.getPageSize());

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectListPriorLnbns(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		PageInfo<ConfmProcessManageVo> pageInfo = new PageInfo<>(list);

		apiResult.setLinks(new Link(pageInfo));
		apiResult.setData(list);
		return apiResult;
	}

	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectLnbnsList(ConfmProcessManageVo confmProcessManageVo) {

		//PageHelper.startPage(confmProcessManageVo.getCurPage(), confmProcessManageVo.getPageSize());

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectLnbnsList(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();
		/*
		PageInfo<ConfmProcessManageVo> pageInfo = new PageInfo<>(list);

		apiResult.setLinks(new Link(pageInfo));*/
		apiResult.setData(list);
		return apiResult;
	}

	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectLnbnsList2(ConfmProcessManageVo confmProcessManageVo) {

		//PageHelper.startPage(confmProcessManageVo.getCurPage(), confmProcessManageVo.getPageSize());

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectLnbnsList2(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		//PageInfo<ConfmProcessManageVo> pageInfo = new PageInfo<>(list);

		//apiResult.setLinks(new Link(pageInfo));
		apiResult.setData(list);
		return apiResult;
	}
	
	@Override
	public ApiResult<List<ConfmProcessManageVo>> selectProblemPerson(ConfmProcessManageVo confmProcessManageVo) {

		PageHelper.startPage(confmProcessManageVo.getCurPage(), confmProcessManageVo.getPageSize());

		List<ConfmProcessManageVo> list = confmProcessManageDao.selectProblemPerson(confmProcessManageVo);

		ApiResult<List<ConfmProcessManageVo>> apiResult = new ApiResult<List<ConfmProcessManageVo>>();

		PageInfo<ConfmProcessManageVo> pageInfo = new PageInfo<>(list);

		apiResult.setLinks(new Link(pageInfo));
		apiResult.setData(list);
		return apiResult;
	}
	
	@Override
	public ApiResult<Integer> updateProblemPerson(ConfmProcessManageVo confmProcessManageVo) {
		
		ApiResult<Integer> apiResult = new ApiResult<>();
		
		int result = confmProcessManageDao.countByName(confmProcessManageVo);
		
		int rt;
		
		if (result > 0) {
			rt = confmProcessManageDao.updateProblemPerson(confmProcessManageVo);			
		} else {
			rt = confmProcessManageDao.insertProblemPerson(confmProcessManageVo);
		}
		
		if (rt > 0) {
			apiResult.setStatus(UniComnContents.API_RT_SUCCESS);
			apiResult.setMessage("선택한 인원이 견학 주의자로 등록 되었습니다.");
		} else {
			apiResult.setStatus(UniComnContents.API_RT_FAIL);
			apiResult.setMessage("선택한 인원이 견학 주의자로 등록이 되지 않았습니다.");
		}
		return apiResult;
	}
}