
import java.io.Serializable;

/**
 * 사용자
 * @author 김성배
 */
public class Customer implements Serializable {

	static final long serialVersionUID = 1403909333884478304L;

	/**
	 * 유저아이디
	 */
	private String user_id;

	/**
	 * 고객번호
	 */
	private String cust_no;

	/**
	 * 고객명
	 */
	private String cust_name;

	/**
	 * 주민등록번호
	 */
	private String ssn;

	/**
	 * 이메일
	 */
	private String email;

	/**
	 * 이메일수신여부(Y/N)
	 */
	private String email_rcv;

	/**
	 * 생년월일
	 */
	private String birthday;

	/**
	 * 음력여부
	 */
	private String bir_solar_yn;

	/**
	 * 우편번호
	 */
	private String zipcode_seq;

	/**
	 * 집주소 우편번호
	 */
	private String homezipcode;

	/**
	 * 집주소 시/도
	 */
	private String homesido;

	/**
	 * 집주소 시/구
	 */
	private String homesigu;

	/**
	 * 집주소 동
	 */
	private String homedong;

	/**
	 *
	 */
	private String homedarang;

	/**
	 * 
	 */
	private String homedoseo;

	/**
	 * 번지
	 */
	private String addr_bunge;

	private String addr_reside;

	/**
	 * 아파트 동
	 */
	private String apt_dong;

	/**
	 * 아파트 호
	 */
	private String apt_ho;

	/**
	 * 집전화 지역번호
	 */
	private String home_ddd;

	/**
	 * 집전화 국번
	 */
	private String home_phone1;

	/**
	 * 집전화 번호
	 */
	private String home_phone2;

	/**
	 * 핸드폰 사업자 번호
	 */
	private String mobile_ddd;

	/**
	 * 핸드폰 국번
	 */
	private String mobile_phone1;

	/**
	 * 핸드폰 번호
	 */
	private String mobile_phone2;

	private String ename_first;

	private String ename_last;

	/**
	 * 직종
	 */
	private String work_class;

	/**
	 * 직업
	 */
	private String work_name;

	/**
	 * 부서
	 */
	private String work_dept;

	/**
	 * 직장 우편번호 일련번호
	 */
	private String work_zipcode_seq;

	/**
	 * 직장 우편번호
	 */
	private String workzipcode;

	/**
	 * 직장 시/도
	 */
	private String worksido;

	/**
	 * 직장 시/구
	 */
	private String worksigu;

	/**
	 * 직장 동
	 */
	private String workdong;

	private String workdarang;

	private String workdoseo;

	/**
	 * 직장 번지
	 */
	private String work_bunge;

	private String work_reside;

	private String work_apt_dong;

	private String work_apt_ho;

	/**
	 * 결혼 여부
	 */
	private String marry_yn;

	/**
	 * 결혼 기념일
	 */
	private String marry_date;

	/**
	 * 여권번호
	 */
	private String passport_num;

	private String addition_yn;

	/**
	 * 직장 전화 지역번호
	 */
	private String work_ddd;

	/**
	 * 직장 전화 국번
	 */
	private String work_phone1;

	/**
	 * 직장 전화번호
	 */
	private String work_phone2;

	/**
	 * 케이블 TV 여부
	 */
	private String catv_yn;

	private String use_dept;

	/**
	 * 위성 TV 여부
	 */
	private String sattv_yn;

	/**
	 * SMS 수신여부
	 */
	private String sms_yn;

	/* 프로시져의 리턴값을 위한 property */
	/**
	 * 성공여부
	 */
	private String ok;

	/**
	 * 에러코드
	 */
	private String err_code;

	/**
	 * 에러메시지
	 */
	private String err_msg;

	/**
	 * 가입일
	 */
	private String enter_date;

	public String getEnter_date() {
		return this.enter_date;
	}
	
	public String getHome_tel() {
		return this.home_ddd + "-" + this.home_phone1 + "-" + this.home_phone2;
	}

	public String getHand_phone() {
		return this.mobile_ddd + "-" + this.mobile_phone1 + "-" + this.mobile_phone2;
	}

	public String getCompany_tel() {
		return this.work_ddd + "-" + this.work_phone1 + "-" + this.work_phone2;
	}

	public String getAddition_yn() {
		return addition_yn;
	}

	public void setAddition_yn(String addition_yn) {
		this.addition_yn = addition_yn;
	}

	public String getAddr_bunge() {
		return addr_bunge;
	}

	public void setAddr_bunge(String addr_bunge) {
		this.addr_bunge = addr_bunge;
	}

	public String getAddr_reside() {
		return addr_reside;
	}

	public void setAddr_reside(String addr_reside) {
		this.addr_reside = addr_reside;
	}

	public String getApt_dong() {
		return apt_dong;
	}

	public void setApt_dong(String apt_dong) {
		this.apt_dong = apt_dong;
	}

	public String getApt_ho() {
		return apt_ho;
	}

	public void setApt_ho(String apt_ho) {
		this.apt_ho = apt_ho;
	}

	public String getBir_solar_yn() {
		return bir_solar_yn;
	}

	public void setBir_solar_yn(String bir_solar_yn) {
		this.bir_solar_yn = bir_solar_yn;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCatv_yn() {
		return catv_yn;
	}

	public void setCatv_yn(String catv_yn) {
		this.catv_yn = catv_yn;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	
	public void setCustName(String custName){
		this.custName = custName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail_rcv() {
		return email_rcv;
	}

	public void setEnter_date(String enter_date) {
		this.enter_date = enter_date;
	}
	
	public void setEmail_rcv(String email_rcv) {
		this.email_rcv = email_rcv;
	}

	public String getEname_first() {
		return ename_first;
	}

	public void setEname_first(String ename_first) {
		this.ename_first = ename_first;
	}

	public String getEname_last() {
		return ename_last;
	}

	public void setEname_last(String ename_last) {
		this.ename_last = ename_last;
	}

	public String getHome_ddd() {
		return home_ddd;
	}

	public void setHome_ddd(String home_ddd) {
		this.home_ddd = home_ddd;
	}

	public String getHome_phone1() {
		return home_phone1;
	}

	public void setHome_phone1(String home_phone1) {
		this.home_phone1 = home_phone1;
	}

	public String getHome_phone2() {
		return home_phone2;
	}

	public void setHome_phone2(String home_phone2) {
		this.home_phone2 = home_phone2;
	}

	public String getHomedarang() {
		return homedarang;
	}

	public void setHomedarang(String homedarang) {
		this.homedarang = homedarang;
	}

	public String getHomedong() {
		return homedong;
	}

	public void setHomedong(String homedong) {
		this.homedong = homedong;
	}

	public String getHomedoseo() {
		return homedoseo;
	}

	public void setHomedoseo(String homedoseo) {
		this.homedoseo = homedoseo;
	}

	public String getHomesido() {
		return homesido;
	}

	public void setHomesido(String homesido) {
		this.homesido = homesido;
	}

	public String getHomesigu() {
		return homesigu;
	}

	public void setHomesigu(String homesigu) {
		this.homesigu = homesigu;
	}

	public String getHomezipcode() {
		return homezipcode;
	}

	public void setHomezipcode(String homezipcode) {
		this.homezipcode = homezipcode;
	}

	public String getMarry_date() {
		return marry_date;
	}

	public void setMarry_date(String marry_date) {
		this.marry_date = marry_date;
	}

	public String getMarry_yn() {
		return marry_yn;
	}

	public void setMarry_yn(String marry_yn) {
		this.marry_yn = marry_yn;
	}

	public String getMobile_ddd() {
		return mobile_ddd;
	}

	public void setMobile_ddd(String mobile_ddd) {
		this.mobile_ddd = mobile_ddd;
	}

	public String getMobile_phone1() {
		return mobile_phone1;
	}

	public void setMobile_phone1(String mobile_phone1) {
		this.mobile_phone1 = mobile_phone1;
	}

	public String getMobile_phone2() {
		return mobile_phone2;
	}

	public void setMobile_phone2(String mobile_phone2) {
		this.mobile_phone2 = mobile_phone2;
	}

	public String getPassport_num() {
		return passport_num;
	}

	public void setPassport_num(String passport_num) {
		this.passport_num = passport_num;
	}

	public String getSattv_yn() {
		return sattv_yn;
	}

	public void setSattv_yn(String sattv_yn) {
		this.sattv_yn = sattv_yn;
	}

	public String getSms_yn() {
		return sms_yn;
	}

	public void setSms_yn(String sms_yn) {
		this.sms_yn = sms_yn;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getUse_dept() {
		return use_dept;
	}

	public void setUse_dept(String use_dept) {
		this.use_dept = use_dept;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getWork_apt_dong() {
		return work_apt_dong;
	}

	public void setWork_apt_dong(String work_apt_dong) {
		this.work_apt_dong = work_apt_dong;
	}

	public String getWork_apt_ho() {
		return work_apt_ho;
	}

	public void setWork_apt_ho(String work_apt_ho) {
		this.work_apt_ho = work_apt_ho;
	}

	public String getWork_bunge() {
		return work_bunge;
	}

	public void setWork_bunge(String work_bunge) {
		this.work_bunge = work_bunge;
	}

	public String getWork_class() {
		return work_class;
	}

	public void setWork_class(String work_class) {
		this.work_class = work_class;
	}

	public String getWork_ddd() {
		return work_ddd;
	}

	public void setWork_ddd(String work_ddd) {
		this.work_ddd = work_ddd;
	}

	public String getWork_dept() {
		return work_dept;
	}

	public void setWork_dept(String work_dept) {
		this.work_dept = work_dept;
	}

	public String getWork_name() {
		return work_name;
	}

	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}

	public String getWork_phone1() {
		return work_phone1;
	}

	public void setWork_phone1(String work_phone1) {
		this.work_phone1 = work_phone1;
	}

	public String getWork_phone2() {
		return work_phone2;
	}

	public void setWork_phone2(String work_phone2) {
		this.work_phone2 = work_phone2;
	}

	public String getWork_reside() {
		return work_reside;
	}

	public void setWork_reside(String work_reside) {
		this.work_reside = work_reside;
	}

	public String getWork_zipcode_seq() {
		return work_zipcode_seq;
	}

	public void setWork_zipcode_seq(String work_zipcode_seq) {
		this.work_zipcode_seq = work_zipcode_seq;
	}

	public String getWorkdarang() {
		return workdarang;
	}

	public void setWorkdarang(String workdarang) {
		this.workdarang = workdarang;
	}

	public String getWorkdong() {
		return workdong;
	}

	public void setWorkdong(String workdong) {
		this.workdong = workdong;
	}

	public String getWorkdoseo() {
		return workdoseo;
	}

	public void setWorkdoseo(String workdoseo) {
		this.workdoseo = workdoseo;
	}

	public String getWorksido() {
		return worksido;
	}

	public void setWorksido(String worksido) {
		this.worksido = worksido;
	}

	public String getWorksigu() {
		return worksigu;
	}

	public void setWorksigu(String worksigu) {
		this.worksigu = worksigu;
	}

	public String getWorkzipcode() {
		return workzipcode;
	}

	public void setWorkzipcode(String workzipcode) {
		this.workzipcode = workzipcode;
	}

	public String getZipcode_seq() {
		return zipcode_seq;
	}

	public void setZipcode_seq(String zipcode_seq) {
		this.zipcode_seq = zipcode_seq;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getCust_no() {
		return cust_no;
	}

	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}
}
