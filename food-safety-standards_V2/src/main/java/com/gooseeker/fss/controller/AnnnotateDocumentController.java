package com.gooseeker.fss.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.commons.entity.ResponseJsonMsg;
import com.gooseeker.fss.commons.generic.controller.GenericController;
import com.gooseeker.fss.commons.mapper.common.SUserExample;
import com.gooseeker.fss.commons.mapper.common.SUserExample.Criteria;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.DocReplace;
import com.gooseeker.fss.entity.MyUser;
import com.gooseeker.fss.entity.PageInclude;
import com.gooseeker.fss.entity.StandardsUser;
import com.gooseeker.fss.entity.vo.MarkProgressVo;
import com.gooseeker.fss.handlers.TxtHandler;
import com.gooseeker.fss.index.AnnotateIndexUtil;
import com.gooseeker.fss.mapper.StandardsUserMapper;
import com.gooseeker.fss.mapper.UserMapper;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.AnnotateWordService;
import com.gooseeker.fss.service.DocReplaceService;
import com.gooseeker.fss.service.DocumentAuthorityHandlerService;
import com.gooseeker.fss.service.PageIncludeService;

@Controller
@RequestMapping("standards/doc")
public class AnnnotateDocumentController extends GenericController
{
    protected static Logger logger = Logger.getLogger(AnnnotateDocumentController.class);

    @Resource(name = "annotateDocumentServiceImpl")
    private AnnotateDocumentService annotateDocumentService;

    @Resource(name = "annotateWordServiceImpl")
    private AnnotateWordService annotateWordService;

    @Resource(name = "pageIncludeServiceImpl")
    private PageIncludeService pageIncludeService;
    
    @Resource(name ="documentAuthorityHandlerServiceImpl")
    private DocumentAuthorityHandlerService authorityHandlerService;
    
    @Resource(name = "docReplaceServiceImpl")
    private DocReplaceService docReplaceService;
    
    @Resource(name = "userMapper")
    private UserMapper userMapper;
    
    /**
     * 首页 打标进程
     * @param model
     * @return
     */
    @RequestMapping("/fss.html")
    public String fss(Model model)
    {
        SUserExample example2 = new SUserExample();
        
        example2.setOrderByClause("EXPIRATION DESC");
        Criteria aa = example2.createCriteria();
        //aa.andEqualTo("NAME", "yy");
        aa.andGreaterThan("CREATEDATE", "2017-09-13 14:46:37");
        aa.andNotEqualTo("NAME", "yy");
        
        List<MyUser> users = userMapper.selectByExample(example2);
        for (int i = 0; i < users.size(); i++)
        {
            System.out.println(users.get(i).toString());
        }
        
        SUserExample example = new SUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("USER_ID", 1);
        
        MyUser user = new MyUser();
        user.setEmail("test@qq.com");
        userMapper.updateByExampleSelective(user, example);
        
        
        MarkProgressVo progress = annotateDocumentService.getMarkProgress();
        model.addAttribute("progress", progress);
        return "fss";
    }

    /**
     * 文档列表
     * @param model
     * @param userName 当前用户名
     * @param roleLv 用户的权限等级
     * @param tagState 文档的打标状态
     * @param p 当前页数
     * @return
     */
    @RequestMapping(value = "/docItems.html")
    public String docItems(Model model, @ModelAttribute("userName") String userName,
        @ModelAttribute("roleLv") String roleLv,
        @RequestParam("tagState") String tagState,
        @RequestParam(value = "p", defaultValue = "1") int p,
        @RequestParam(value = "view", defaultValue = "1") int view)
    {
        docItems(model, null, tagState, null, userName, roleLv, p, view);
        return "doc/docItems";
    }

    /**
     * 文档管理页
     * @param model
     * @param userName 当前用户名
     * @param roleLv 用户的权限等级
     * @param tagState 文档的打标状态
     * @param p 当前页数
     * @return
     */
    @RequestMapping("/docManage.html")
    public String docManage(Model model, @ModelAttribute("userName") String userName,
        @ModelAttribute("roleLv") String roleLv,
        @RequestParam(value = "tagState", required = false) String tagState,
        @RequestParam(value = "p", defaultValue = "1") int p)
    {
        //docItems(model, null, tagState, null, userName, roleLv, p, );
        return "doc/docManage";
    }
    
    /**
     * 分页根据条件查出文档
     * @param model
     * @param type 文档类型
     * @param tagState 文档状态
     * @param country 国家
     * @param user 用户
     * @param roleLv 用户的权限等级
     * @param pageNum 当前页数
     */
    public void docItems(Model model, String type, String tagState, 
            String country, String user, String roleLv, int pageNum, int view)
    {
        //查询条件构建
        QueryConditionContainer container = new QueryConditionContainer(type, tagState,
            country, user, roleLv, pageNum, null);
        PageInfo<AnnotateDocument> page = annotateDocumentService.findByPage(container);
        model.addAttribute("page", page);
        model.addAttribute("view", view);
        model.addAttribute("tagState", container.getOutputTagState());
    }

//    /**
//     * 打标状态确认
//     * @param model
//     * @param request
//     * @param response
//     * @param docNos
//     * @throws Exception 
//     * @see [类、类#方法、类#成员]
//     */
//    @RequestMapping("/stateConfirm.html")
//    public void stateConfirm(Model model, HttpServletResponse response,
//            @ModelAttribute("roleLv") String roleLv,
//            @ModelAttribute("userName") String userName,
//            @RequestParam("ids[]") long[] ids,
//            @RequestParam("status") int status,
//            @RequestParam(value = "flag", required = false) String flag) throws Exception
//    {
//        ResponseJsonMsg msg = new ResponseJsonMsg(true);
//        StringBuilder errorNoBuilder = new StringBuilder();
//        if(ids != null && ids.length > 0)
//        {
//            List<PageInclude> includes = new ArrayList<PageInclude>();
//            String version = String.valueOf(System.currentTimeMillis());
//            for (int i = 0; i < ids.length; i++)
//            {
//                AnnotateDocument doc = annotateDocumentService.selectByPrimaryKey(ids[i]);
//                String standardNo = doc.getStandardNo();
//                try
//                {
//                    //操作权限检查
//                    if(authorityHandlerService.authorityCheckForStateUpdate(doc, userName, roleLv, status))
//                    {
//                        doc.setTagState(status);
//                        if("synch".equals(flag))
//                        {
//                            //入库状态--》未开始状态则需要同步文档的关键词
//                            /**
//                             * 1、找出需要同步的关键词，比文档的版本更高的版本的关键词
//                             * 2、同步的时候需要重新获得该文档对应的页面所包含的关键词信息，pageinclude对象信息
//                             * 3、更新文档的关键词版本
//                             */
//                            List<String> words = annotateWordService.findHighVersionWords(doc.getVersion());
//                            Map<String, Object> map = TxtHandler.parseTxtText(doc, words);
//                            List<PageInclude> pageIncludes = (List<PageInclude>) map.get("pageIncludes");
//                            includes.addAll(pageIncludes);
//                            doc.setVersion(version);
//                        }
//                        annotateDocumentService.saveOrUpdate(doc);
//                    }
//                }
//                catch(Exception e)
//                {
//                    msg.setSuccess(false);
//                    errorNoBuilder.append(standardNo).append(Constant.SPLIT_COMMA);
//                    logger.error("文档编号：" + standardNo + "状态确认为" + status + "出错", e);
//                }
//            }
//            if(includes.size() > 0)
//            {
//                pageIncludeService.addPageIncludes(includes);
//            }
//            msg.setErrorMsg(errorNoBuilder.toString());
//            msg.write(response);
//        }
//    }
//    
    /**
     * 打标状态确认
     * @param model
     * @param request
     * @param response
     * @param docNos
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/docStateUpdate.html")
    public void docStateUpdate(Model model, HttpServletResponse response,
            @ModelAttribute("roleLv") String roleLv,
            @ModelAttribute("userName") String userName,
            @RequestParam("docIds[]") long[] docIds,
            @RequestParam("updateState") int updateState,
            @RequestParam(value = "flag", required = false) String flag) throws Exception
    {
        ResponseJsonMsg msg = new ResponseJsonMsg(true);
        StringBuilder errorNoBuilder = new StringBuilder();
        if(docIds != null && docIds.length > 0)
        {
            List<PageInclude> includes = new ArrayList<PageInclude>();
            String version = String.valueOf(System.currentTimeMillis());
            for (int i = 0; i < docIds.length; i++)
            {
                AnnotateDocument doc = annotateDocumentService.selectByPrimaryKey(docIds[i]);
                String standardNo = doc.getStandardNo();
                try
                {
                    //操作权限检查
                    if(authorityHandlerService.authorityCheckForStateUpdate(doc, userName, roleLv, updateState))
                    {
                        doc.setTagState(updateState);
                        if("synch".equals(flag))
                        {
                            //入库状态--》未开始状态则需要同步文档的关键词
                            /**
                             * 1、找出需要同步的关键词，比文档的版本更高的版本的关键词
                             * 2、同步的时候需要重新获得该文档对应的页面所包含的关键词信息，pageinclude对象信息
                             * 3、更新文档的关键词版本
                             */
                            List<String> words = annotateWordService.findHighVersionWords(doc.getVersion());
                            Map<String, Object> map = TxtHandler.parseTxtText(doc, words);
                            List<PageInclude> pageIncludes = (List<PageInclude>) map.get("pageIncludes");
                            includes.addAll(pageIncludes);
                            doc.setVersion(version);
                        }
                        annotateDocumentService.saveOrUpdate(doc);
                    }
                    else
                    {
                        msg.setSuccess(false);
                        errorNoBuilder.append(standardNo).append(Constant.SPLIT_COMMA);
                    }
                }
                catch(Exception e)
                {
                    msg.setSuccess(false);
                    errorNoBuilder.append(standardNo).append(Constant.SPLIT_COMMA);
                    logger.error("文档编号：" + standardNo + "状态确认为" + updateState + "出错", e);
                }
            }
            if(includes.size() > 0)
            {
                pageIncludeService.addPageIncludes(includes);
            }
            msg.setErrorMsg(errorNoBuilder.toString());
            msg.write(response);
        }
    }
    
    /**
     * 
     * @param model
     * @param userName 用户名
     * @param roleLv 用户权限等级
     * @param docId 文档id
     * @param edit 是否是编辑页面
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit.html")
    public String edit(Model model,
            @ModelAttribute("userName") String userName,
            @ModelAttribute("roleLv") String roleLv,
            @RequestParam("docId") long docId,
            @RequestParam(value = "edit", required = false) boolean edit) throws Exception
    {
        AnnotateDocument doc = annotateDocumentService.selectByPrimaryKey(docId);
        if (doc != null && authorityHandlerService.hasOperateAuthority(doc, userName, roleLv))
        {
            // 代替关系
            List<DocReplace> docReplaces = docReplaceService.findDocReplacesBydocNo(doc.getStandardNo());
            model.addAttribute("docReplaces", docReplaces);
            model.addAttribute("doc", doc);
        }
        if (edit)
        {
            return "doc/editDoc";
        }
        return "doc/editDocPreview";
    }
    
    /**
     * 保存编辑的信息
     * 
     * @param model
     * @param request
     * @param response
     * @throws IOException 
     */
    @RequestMapping("/saveEdit.html")
    public String saveEdit(Model model, AnnotateDocument doc, HttpServletResponse response,
            @RequestParam("queryCondition.startDate") String dates,
            @ModelAttribute("userName") String userName,
            @ModelAttribute("roleLv") String roleLv) throws Exception
    {
        AnnotateDocument dbDoc = annotateDocumentService.selectByPrimaryKey(doc.getId());
        if(StringUtil.isNotBlank(dates) && authorityHandlerService.hasOperateAuthority(dbDoc, userName, roleLv))
        {
            String[] dateArray = dates.split(Constant.SPLIT_COMMA);
            if(dateArray.length == 2)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date pubDate = sdf.parse(dateArray[0]);
                doc.setPubTime(new Timestamp(pubDate.getTime()));
                Date impDate = sdf.parse(dateArray[1]);
                doc.setImpTime(new Timestamp(impDate.getTime()));
            }
            doc.setModiUser(userName);
            doc.setModiTime(new Timestamp(System.currentTimeMillis()));
            annotateDocumentService.update(doc);
            
            AnnotateIndexUtil indexUtil = new AnnotateIndexUtil();
            //重新从数据库中获取更新后的文档信息
            dbDoc = annotateDocumentService.selectByPrimaryKey(doc.getId());
            //去找到文档对应的txt文件取出里面的内容设置到doc对象的text属性中
            String text = TxtHandler.readTxt(dbDoc);
            dbDoc.setText(text);
            //更新索引
            indexUtil.updateIndex(dbDoc);
            docReplaceService.addDocReplaceByCompare(doc.getDocReplaces());
            
        }
        return "redirect:/standards/doc/edit.html?docId=" + doc.getId() + "&edit=false";
    }
    
    /**
     * 删除文档（只删除文档信息、文档的打标信息部分未被删除）
     * @param request
     * @param response
     * @param ids
     * @throws IOException 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delete.html")
    public void delete(HttpServletResponse response,
        @ModelAttribute("userName") String userName,
        @ModelAttribute("roleLv") String roleLv,
        @RequestParam(value = "ids[]") Long[] ids) throws IOException
    {
        ResponseJsonMsg msg = new ResponseJsonMsg(false);
        if(Constant.ROLE_LV_STR_ADMIN.equals(roleLv))//只有管理员才有删除的权限
        {
            try
            {
                annotateDocumentService.deleteByPrimaryKeys(ids);
                msg.setSuccess(true);
            }
            catch (Exception e)
            {
                logger.error("删除文档出错", e);
            }
        }
        msg.write(response);
    }
    
    //-------------------代替关系处理------------------------
    /**
     * 文档代替关系
     * @param model
     * @param request
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping("/docReplace.html")
    public String showDocReplace(Model model, HttpServletRequest request) throws Exception
    {
        String p = request.getParameter("p");
        int currentPage = (p == null) ? 1 : Integer.valueOf(p);
        Page<DocReplace> page = docReplaceService.findDocReplace(currentPage);
        String user = request.getUserPrincipal().getName();
        String roleLv = standardsUserService.getUserRoleLv(user);
        model.addAttribute("roleLv", roleLv);
        model.addAttribute("page", page);
        return "doc/docReplace";
    }

    /**
     * 代替关系历史记录
     */
    @RequestMapping("/docRepHistory.html")
    public String findDocReplace(Model model, HttpServletRequest request,
            @RequestParam(value = "docNo", required = true) String docNo) throws Exception
    {
        String p = request.getParameter("p");
        String oldDocNo = request.getParameter("oldDocNo");
        int currentPage = (p == null) ? 1 : Integer.valueOf(p);
        if (StringUtil.isNotBlank(oldDocNo) && StringUtil.isNotBlank(docNo))
        {
            // 根据文档编号分页查询出历史记录
            Page<DocReplace> page = docReplaceService.findDocReplaceByDocNo(currentPage, docNo, oldDocNo, Constant.PAGE_LIMIT);
            String user = request.getUserPrincipal().getName();
            String roleLv = standardsUserService.getUserRoleLv(user);
            model.addAttribute("roleLv", roleLv);
            model.addAttribute("page", page);
            model.addAttribute("docNo", docNo);
            model.addAttribute("oldDocNo", oldDocNo);
        }
        return "doc/docRepHistory";
    }

    /**
     * 更新文档代替关系
     * @throws IOException 
     */
    @RequestMapping(value = "/alterReplace.html", method = RequestMethod.POST)
    public void alterReplace(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ResponseJsonMsg msg = new ResponseJsonMsg(true);
        String id = request.getParameter("id");
        String newDoc = request.getParameter("newDoc");
        String oldDoc = request.getParameter("oldDoc");
        String relation = request.getParameter("relation");
        String remark = request.getParameter("remark");
        String username = request.getUserPrincipal().getName();
        StandardsUser standardsUser = standardsUserService.findByUserName(username);
        if (StringUtil.isNumerical(id) && StringUtil.isNotBlank(username)
                && StringUtil.isNotBlank(newDoc) && StringUtil.isNotBlank(oldDoc)
                && StringUtil.isNotBlank(relation))
        {
            DocReplace replace = new DocReplace();
            replace.setId(Long.valueOf(id));
            replace.setNewDoc(newDoc);
            replace.setOldDoc(oldDoc);
            replace.setRelation(relation);
            replace.setRemark(remark);
            replace.setCreateUserName(String.valueOf(standardsUser.getId()));
            replace.setCreateTime(new Timestamp(System.currentTimeMillis()));
            try
            {
                // 先查询改记录是否存在
                DocReplace rpc = docReplaceService.selectByPrimaryKey(Long.valueOf(id));
                if (rpc != null)
                {
                    // 存在，判断是否有修改
                    if (!replace.docReplaceEquals(rpc))
                    {
                        // 有修改，把原来的记录设置为历史的状态
                        // 把新增改动的记录
                        docReplaceService.updateDocReplaceDelStatus(Long.valueOf(id), 1);
                        docReplaceService.addDocReplace(replace);
                    }
                }
            }
            catch (Exception e)
            {
                logger.error("更新文档代替关系出错", e);
                msg.setSuccess(false);
            }
        }
        msg.write(response);
    }

    /**
     * 删除代替关系
     */
    @RequestMapping("/delReplace.html")
    public void delReplace(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "id", required = true) String docId) throws Exception
    {
        String msg = "error";
        String username = request.getUserPrincipal().getName();
        if (StringUtil.isNumerical(docId))
        {
            StandardsUser standardsUser = standardsUserService.findByUserName(username);
            if (standardsUser != null)
            {
                try
                {
                    // 删除代替关系，把del标志设置为删除的状态
                    docReplaceService.delDocReplace(docId, String.valueOf(standardsUser.getId()));
                    msg = "success";
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        response.getWriter().write(msg);
    }

    /**
     * 新增文档代替关系
     */
    @RequestMapping("/saveAddReplace.html")
    public void saveAddReplace(HttpServletRequest request,
            HttpServletResponse response, Model model) throws Exception
    {
        String msg = "error";
        String newDoc = request.getParameter("newDoc");
        String oldDoc = request.getParameter("oldDoc");
        String relation = request.getParameter("relation");
        String remark = request.getParameter("remark");
        String username = request.getUserPrincipal().getName();
        StandardsUser standardsUser = standardsUserService.findByUserName(username);
        if (StringUtil.isNotBlank(username) && StringUtil.isNotBlank(newDoc)
                && StringUtil.isNotBlank(oldDoc) && StringUtil.isNotBlank(relation))
        {
            DocReplace replace = new DocReplace();
            replace.setNewDoc(newDoc);
            replace.setOldDoc(oldDoc);
            replace.setRelation(relation);
            replace.setRemark(remark);
            replace.setCreateUserName(String.valueOf(standardsUser.getId()));
            replace.setCreateTime(new Timestamp(System.currentTimeMillis()));
            try
            {
                docReplaceService.addDocReplace(replace);
                msg = "success";
            }
            catch (Exception e)
            {
                logger.error("添加文档代替关系出错", e);
            }
        }
        response.getWriter().write(msg);
    }
}
