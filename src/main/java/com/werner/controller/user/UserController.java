package com.werner.controller.user;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.StringUtils;
import com.werner.pojo.Role;
import com.werner.pojo.User;
import com.werner.service.role.RoleServiceImpl;
import com.werner.service.user.UserServiceImpl;
import com.werner.util.Constants;
import com.werner.util.PageUtil;
import com.werner.vo.PwdModifyReg;
import com.werner.vo.ResponseBean;
import com.werner.vo.UserCreateReq;
import com.werner.vo.UserQueryReq;
import com.werner.vo.UserUpdateReq;

@Controller
public class UserController {

	@Autowired
	private UserServiceImpl userService;	
	@Autowired
	private RoleServiceImpl roleService;

	//修改密碼
	@PostMapping("/account/updatePwd")	
	public String updatePwd(HttpSession session, PwdModifyReg req, Model model){

		Object userObj = session.getAttribute(Constants.USER_SESSION);
		String newpassword = req.getNewpassword();
		boolean flag = true;

		if(userObj != null && newpassword != null) {
			flag = userService.updatePwd(((User)userObj).getId(), newpassword);

			if(flag) {
				model.addAttribute(Constants.PWD_MESSAGE, "密碼修改成功");
				session.removeAttribute(Constants.USER_SESSION);
			}else {
				model.addAttribute(Constants.PWD_MESSAGE, "密碼修改失敗");
			}
		}else {
			model.addAttribute(Constants.PWD_MESSAGE, "新密碼出現異常，請重新輸入");
		}
		return "pwdmodify";
	}	

	//舊密碼驗證
	@PatchMapping("/account/verify")	
	@ResponseBody
	public ResponseBean<PwdModifyReg> pwdVerify(HttpSession session, @RequestBody PwdModifyReg req) {

		Object userObj = session.getAttribute(Constants.USER_SESSION);
		String oldpassword = req.getOldpassword();
		String message = "";
		//		List<PwdModifyReg> datalist = new ArrayList<PwdModifyReg>(List.of(req));
		List<PwdModifyReg> datalist = Collections.emptyList();

		if(userObj == null) {
			message = "sessionerror";  
		}else if(StringUtils.isNullOrEmpty(oldpassword)) {
			message = "error";
		}else {
			message = "true";
		}		
		return ResponseBean.<PwdModifyReg>builder().message(message).responseStatus(ResponseBean.OK).dataList(datalist).build();	
	}

	//用條件查詢使用者們
	@GetMapping("/view/userlist")  
	public String queryUserList(HttpSession session, UserQueryReq req, Model model) throws SQLException {

		List<User> userList = null;
		List<Role> roleList = null;

		String queryUserName = req.getQueryname();  //使用者名稱
		String queryUserRoleStr = req.getQueryUserRole();
		String pageIndexStr = req.getPageIndex();

		/*===========設定頁數開始================*/		
		int queryUserRole = 0;  //使用者角色編號
		int currentPageNo = 1;   //當前頁數
		int perPageSize = 5 ;  //每頁最大顯示數量

		//使用者名稱，未輸入時(預設)
		if(queryUserName == null) {
			queryUserName = "";
		}
		//使用者角色編號
		if(queryUserRoleStr != null && !queryUserRoleStr.equals("")) {
			queryUserRole = Integer.parseInt(queryUserRoleStr);
		}
		//當前頁數
		if(pageIndexStr != null && !pageIndexStr.equals("")) {
			currentPageNo = Integer.parseInt(pageIndexStr);
		}
		//總使用者數量
		int totalUserCount = userService.getUserCount(queryUserName, queryUserRole);

		PageUtil pageUtil = new PageUtil();
		pageUtil.setCurrentPage(currentPageNo);  //設定當前頁數
		pageUtil.setPerPageSize(perPageSize);  //設定每頁最大顯示數量
		pageUtil.setTotalUserCount(totalUserCount);  //設定所有使用者數
		int totalPageCount = pageUtil.getTotalPageCount();  //獲得總頁數
		System.out.println("totalPageCount=" +totalPageCount);

		if(totalPageCount<1) {
			totalPageCount = 1;
		}else if(currentPageNo>totalPageCount) {
			currentPageNo = totalPageCount;
		}
		/*===========設定頁數完成================*/

		//使用者列表
		userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, perPageSize);
		model.addAttribute("userList", userList);
		//角色列表
		roleList = roleService.getRoleList();
		model.addAttribute("roleList", roleList);

		model.addAttribute("totalPageCount", totalPageCount);  //總頁數
		model.addAttribute("totalUserCount", totalUserCount);  //總人數
		model.addAttribute("currentPageNo", currentPageNo);    //當前頁數
		model.addAttribute("queryUserName",queryUserName);     //查詢使用者名稱
		model.addAttribute("queryUserRole",queryUserRole);     //查詢使用者角色

		return "userlist";		
	}

	//新增使用者
	@PostMapping("/account")   //account + postmapping
	public String createUser(HttpSession session, UserCreateReq req) {

		boolean flag = false;

		String userCode = req.getUserCode();
		String userName = req.getUserName();
		String userPassword = req.getUserPassword();
		String gender_str = req.getGender();
		String birthday_str = req.getBirthday();
		String phone = req.getPhone();
		String address = req.getAddress();
		String userRole_str = req.getUserRole();
		User user = new User();

		int gender = 0;
		int userRole = 0;
		Date birthday = new Date();

		if(!StringUtils.isNullOrEmpty(gender_str)) {
			gender = Integer.parseInt(gender_str);
		}
		if(!StringUtils.isNullOrEmpty(userRole_str)) {
			userRole = Integer.parseInt(userRole_str);
		}
		if(!StringUtils.isNullOrEmpty(birthday_str)) {
			try {
				birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthday_str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		user.setUserCode(userCode);
		user.setUserName(userName);
		user.setUserPassword(userPassword);
		user.setGender(gender);
		user.setBirthday(birthday);
		user.setPhone(phone);
		user.setAddress(address);
		user.setUserRole(userRole);
		user.setCreationDate(new Date());
		user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());

		flag = userService.createUser(user);

		if(flag) {
			return "redirect:/view/userlist";			
		}else {
			return "useradd";
		}
	} 

	//獲得角色列表
	//大D推薦修正
	@GetMapping("/role") 
	@ResponseBody
	public List<Role> getRoleList() throws SQLException, JsonProcessingException {
		List<Role> roleList = roleService.getRoleList();
		return roleList;
	}

	//查詢使用者是否已存在
	@RequestMapping("account/checkUserName/{userCode}") 
	@ResponseBody
	public String queryExistUser(@PathVariable String userCode) throws JsonProcessingException {

		System.out.println("有進來queryExistUser");
		System.out.println("userCode="+userCode);
		
		ObjectMapper mapper = new ObjectMapper();

		Map<String, String> resultMap = new HashMap<String, String>();

		if(StringUtils.isNullOrEmpty(userCode)) {
			resultMap.put("message", "false");
		}else {
			User user = userService.queryLoginUser(userCode);
			if(user != null) {
				resultMap.put("message", "exist");
			}else {
				resultMap.put("message", "notexist");
			}
		}
		String str = mapper.writeValueAsString(resultMap);
		return str;
	}

	//刪除使用者
	@DeleteMapping("/account/{userId}")  //delete + account/{userId}
	@ResponseBody
	public String deleteUser(@PathVariable String userId) throws JsonProcessingException {

		boolean flag = false;
		Integer id = 0;
		Map<String, String> resultMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();

		if(!StringUtils.isNullOrEmpty(userId)) {
			id = Integer.parseInt(userId);
			flag = userService.deleteUser(id);
			if(flag) {
				resultMap.put("message", "true");
			}else {
				resultMap.put("message", "false");
			}			
		}else {
			resultMap.put("message", "notexist");
		}
		String str = mapper.writeValueAsString(resultMap);
		return str;
	}

	//查詢使用者個人資料  
	@GetMapping("/account/{userId}")  //get + account/{userId}
	public String queryUser(@PathVariable String userId, Model model) {
		User user = new User();
		Integer id = 0;

		if(!StringUtils.isEmptyOrWhitespaceOnly(userId)) {
			id = Integer.parseInt(userId); 
			user = userService.queryUser(id);
			model.addAttribute("user",user);
		} 
		return "userview";
	}	
	
	//訪問帳號修改頁面
	@GetMapping("/view/userModify/{userId}")  
	public ModelAndView viewUserModify(@PathVariable Integer userId) throws ParseException {
		
		ModelAndView mv = new ModelAndView();
		User user = null;
		
		user = userService.queryUser(userId);
		
		if(user != null) {
			mv.addObject("user",user);
			mv.setViewName("usermodify");		
		}else {
			mv.setViewName("userlist");
		}
		return mv;
	}
	
	//post + account/modify/
	@RequestMapping("/account/modify/{userId}")
	public ModelAndView modifyUser(UserUpdateReq req, HttpSession session) throws ParseException {
		
		ModelAndView mv = new ModelAndView();
		
		boolean flag = false;

		String uid_str = req.getUid();
		String userCode = req.getUserCode();
		String userName = req.getUserName();		
		String gender_str = req.getGender();
		String birthday_str = req.getBirthday();
		String phone = req.getPhone();
		String address = req.getAddress();
		String userRole_str = req.getUserRole();
		
		User user = null;
		
		if(!StringUtils.isNullOrEmpty(uid_str)) {
			user = new User();
			user.setId(Integer.parseInt(uid_str));
			user.setUserName(userName);
			user.setGender(Integer.parseInt(gender_str));
			user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday_str));
			user.setPhone(phone);
			user.setAddress(address);
			user.setUserRole(Integer.parseInt(userRole_str));
			
			Object userObj = session.getAttribute(Constants.USER_SESSION);
			user.setModifyBy(((User)userObj).getId());
			user.setModifyDate(new Date());
			
			flag = userService.updateUser(user);
			
			if(flag) {
				mv.addObject("user",user);
				mv.setViewName("redirect:/view/userModify/"+user.getId());
			}else {
				mv.setViewName("usermodify");
			}
		}else {
			mv.setViewName("usermodify");
		}
		return mv;

	}
	

}
