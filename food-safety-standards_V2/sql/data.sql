
INSERT INTO `USER` VALUES ('1', 'admin', 'admin', '', 0, 1, '2217-11-06 09:43:23', NOW());
INSERT INTO `authority` VALUES ('1', '1', 'ROLE_ADMIN');

-- ----------------------------
-- Records of country
-- ----------------------------
INSERT INTO `country` VALUES ('16', 'CAC');
INSERT INTO `country` VALUES ('18', 'FAO');
INSERT INTO `country` VALUES ('17', 'ISO');
INSERT INTO `country` VALUES ('15', 'WHO');
INSERT INTO `country` VALUES ('1', '中国');
INSERT INTO `country` VALUES ('5', '俄罗斯');
INSERT INTO `country` VALUES ('4', '加拿大');
INSERT INTO `country` VALUES ('7', '台湾');
INSERT INTO `country` VALUES ('12', '巴西');
INSERT INTO `country` VALUES ('10', '新加坡');
INSERT INTO `country` VALUES ('8', '新西兰');
INSERT INTO `country` VALUES ('14', '日本');
INSERT INTO `country` VALUES ('3', '欧盟');
INSERT INTO `country` VALUES ('6', '泰国');
INSERT INTO `country` VALUES ('11', '澳大利亚');
INSERT INTO `country` VALUES ('2', '美国');
INSERT INTO `country` VALUES ('13', '韩国');
INSERT INTO `country` VALUES ('9', '香港');

-- ----------------------------
-- Records of url_role
-- ----------------------------
INSERT INTO `url_role` VALUES ('25', 'addSynonym.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('19', 'alterReplace.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('24', 'alterSynonym.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('20', 'delReplace.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('23', 'delSynonym.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('7', 'docManage.html', 'ROLE_ANNOTATE');
INSERT INTO `url_role` VALUES ('5', 'docManage.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('18', 'docRepHistory.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('17', 'docReplace.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('6', 'docSearch.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('22', 'docSynonym.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('3', 'editDoc.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('9', 'factorSearch.html', 'ROLE_ANNOTATE');
INSERT INTO `url_role` VALUES ('8', 'factorSearch.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('21', 'saveAddReplace.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('4', 'saveEdit.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('2', 'uploadDoc.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('26', 'uploadHtmlUrl.html', 'ROLE_CHECK');
INSERT INTO `url_role` VALUES ('27', 'doRepeatUpload.html', 'ROLE_CHECK');



