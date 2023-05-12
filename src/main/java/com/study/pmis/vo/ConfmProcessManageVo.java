package com.study.pmis.vo;

import lombok.Data;

import java.util.List;

@Data
public class ConfmProcessManageVo {

	private String stdrDe;			/* 기준 일자 */
	private String stdrYear;		/* 기준 년도 */
	private String stdrMt;			/* 기준 월 */
	private String stdrDay;			/* 기준 일 */
	private String tdrWeek;			/* 기준 주 */
	private String reqstPosblNmprCo;/* 견학 가능 인원 */
	private String lnbnsNmprCo;		/* 견학 신청 인원 */
	private String openYn;			/* 오픈 여부 */
	private String stdrYm; 			/* 기준 년월 */
	private String weekNum;			/* 주 번호 */
	private String maxWeekNum;		/* max 주 번호 */
	private String stdrValue;
	private String lnbnsSchdulId;
	private String reqstPosblCo;	/* 신청 가능 인원 */
	private String reqstRate;		/* 신청 율 */
	private String tme;				/* 회차 */
	private String reqstDe;			/* 신청 일자 */

	private String rm;				/* 비고 */

	private String envSetupCode;
	private String reqstId;
	private String applcntSn; /* 신청자 순번 */
	private String vhcleId; /* 차량 ID */
	private String nativeSeCode; /* 내국인 구분 코드 */
	private String nationCode; /* 국가 코드 */
	private String nm; /* 성명 */
	private String ihidnum; /* 주민등록번호 */
	private String brthdy; /* 생년월일 */
	private String sexdstn; /* 성별 */
	private String zip; /* 우편번호 */
	private String adres; /* 주소 */
	private String detailAdres; /* 상세 주소 */
	private String mbtlnum; /* 휴대폰번호 */
	private String psprnbr; /* 여권번호 */
	private String pasportValidBeginDe; /* 여권 유효 시작 일자 */
	private String pasportValidEndDe; /* 여권 유효 종료 일자 */
	private String indvdlInfoUseAgreAt; /* 개인 정보 이용 동의 여부 */
	private String smsRecptnAgreAt; /* SMS 수신 동의 여부 */
	private String fileAtchAgreAt; /* 파일 첨부 동의 여부 */
	private String selfReqstAt; /* 본인 신청 여부 */
	private String confmDe; /* 승인 일자 */
	private String confmerId; /* 승인자 ID */
	private String confmResnCn; /* 승인 사유 내용 */
	private String reqstSttusCode; /* 신청 상태 코드 */

	private String nativeSeCodeNm;  /* 내국인 구분 코드 명 */
	private String nationCodeNm; /* 국가 코드 명 */
	private String sexdstnNm; /* 성별 명 */
	private String reqstSttusCodeNm; /* 신청 상태 코드 명 */
	private String processSttus; /* 처리 상태 코드 */
	private String processSttusNm; /* 처리 상태 코드 명 */
	private String newProcessSttus; /* 처리 상태 코드(승인 처리시 사용) */

	private String stdrValueSeCode; /* 기관구분 */

	private String opratNmprCo; /* 탑승 정원 */
	private String vhcleNum; /* 차량 호수 */
	private String reqstVhcleRate; /* 차량 탑승 인원 비율 */

	private String applcntNum;     /* 배차인원 */

	private String agreAtNm; /* 개인정보 수집 및 이용동의 */

	private String errMsg; 		/* 오류 메시지 */

	private String chkIdentity;		/* 주민번호 또는 여권번호 */

	private String lnbnsManTy; /* 견학 자 유형 */
	private String lnbnsManTyNm; /* 견학 자 유형 명칭 */
	private String clssNdOfcps; /* 계급 및 직위 */

	private String lnbnsDe; /* 견학 일자 */
	private String reqstChangePosblDe ; /* 신청 변경 가능 일자 */
	private String reqstCanclPosblDe; /* 신청 취소 가능 일자 */
	private String confmDt; /* 승인 일시 */
	//private String confmPrvonsh; /* 승인 사유 */
	private String processPrvonsh; /* 승인 사유 */
	private String canclDt; /* 취소 일시 */
	private String canclPrvonsh; /* 취소 사유 */
	private String lnbnsTimeOdr; /* 견학 시간 차수 */
	private String lnbnsTime; /* 견학 시간 */

	private String applcntId; /* 신청자 ID */

	private String insttId;  /* 기관ID */

	private String reqstDeBegin; /* 신청 일자 시작 */
	private String reqstDeEnd; /* 신청 일자 종료 */
	private String lnbnsDeBegin; /* 견학 일자 시작 */
	private String lnbnsDeEnd; /* 견학 일자 종료 */

	private String rowNum; /* 순번(정렬용) */

	private String fileSn; /* 파일 순번 */
	private String fileNm; /* 파일 명 */
	private String filePath; /* 파일 경로 */
	private String logicFileNm; /* 논리 파일 명 */

	//조회조건
	private String srchReqstDeBegin; /* 신청일자 시작 */
	private String srchReqstDeEnd; /* 신청일자 종료 */
	private String srchLnbnsDeBegin; /* 견학일자 시작 */
	private String srchLnbnsDeEnd; /* 견학일자 종료 */
	private String srchProcessSttus; /* 승인상태 */
	private String srchLnbnsTimeOdr; /* 견학회차 */
	private String srchLnbnsTime; /* 견학시간 */
	private String srchNm; /* 이름검색 */
	private String srchVhcleNum; /* 탑승차량 */
	private String srchPolice; /* 신원조회 */
	private String srchReqstNo; /* 신원조회 */

	private String confmNo; /* 승인번호 */


	private String lnbnsSeCode;
	private String lnbnsSeCodeNm; /* 견학 구분 명 */
	private String lnbnsExecutAt; /* 견학 실행 여부 */
	private String vhcleOdr;


	private String sj; //sms 문자 제목
	private String cn; //sms 문자 본문
	private String rcverTelno; //sms 문자 수신자

	private String menuNo;

	private String policeIdntyInqireResult; //경찰청 신원조회 여부

	private List<ConfmProcessManageVo> list; /* 목록 */

	private String canConfmAt;
	private String histSn;
	private String lnbnsNo;

	private String idntyInqireSn;
	private String listNm;

	private String vhcleIdCk;
	private String vhcleOdrCk;
	private String status;
	private String lnbnsDt;
	private String validEndDe;
	private String extendTime;
	private String today;
	private String stdrTime;
	private String stdrValueBegin;
	private String atchmnflId;
}
