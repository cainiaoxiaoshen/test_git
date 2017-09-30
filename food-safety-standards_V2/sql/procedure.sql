-- ----------------------------
-- Procedure structure for `updateannotate`
-- ----------------------------
DROP PROCEDURE IF EXISTS `updateannotate`;
DELIMITER ;;
CREATE DEFINER=`fss`@`localhost` PROCEDURE `updateannotate`(IN v_antId INT, IN v_type INT, IN v_text VARCHAR(255), IN v_docId VARCHAR(255), IN v_user VARCHAR(64), IN v_grade INT, IN v_flag INT)
BEGIN

	DECLARE v_did BIGINT;#初始的产品
	DECLARE v_product VARCHAR(255);#初始的产品
	DECLARE v_factor VARCHAR(255);#初始的指标
	DECLARE v_num VARCHAR(255);
	DECLARE v_errorCode INT;
	DECLARE v_description VARCHAR(255);
	DECLARE v_oriText VARCHAR(255); #初始值
	DECLARE v_attrName VARCHAR(255); #属性名称
	DECLARE v_update INT;
	DECLARE v_checkGrade VARCHAR(64);


	SELECT 0 INTO v_errorCode;
	
	SELECT `product`, `factor` INTO v_product, v_factor FROM annotate WHERE id = v_antId;

	SELECT v_docId INTO v_did;

	SELECT 0 INTO v_update;

	SELECT '，审核=[一级审核]' INTO v_checkGrade;

	IF v_type = 1 THEN #修改的是产品属性

		SELECT COUNT(*) INTO v_num FROM annotate WHERE docId = v_docId AND product = v_text AND factor = v_factor;
		IF v_num < 1 THEN #表示修改后的 产品、指标唯一键不存在可以修改
			
			SELECT product into v_oriText FROM annotate WHERE id = v_antId;
		  SELECT '产品' INTO v_attrName;
			UPDATE annotate SET product = v_text WHERE id = v_antId;
			#把所有原来的product、factor组合的xpath改成现在product、factor组合
			UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
			#UPDATE annotate_xpath SET product = v_text WHERE product = v_product AND factor = v_factor AND docId = v_docId;
			SELECT 1 INTO v_errorCode;
			#SELECT 1 INTO v_update;
		ELSEIF v_num > 0 THEN #已经存在不可以修改
			SELECT -1 INTO v_errorCode;
		END IF;
	
	ELSEIF v_type = 2 THEN #修改的是指标属性
		SELECT COUNT(*) INTO v_num FROM annotate WHERE docId = v_docId AND product = v_product AND factor = v_text;
		IF v_num < 1 THEN #表示修改后的 产品、指标唯一键不存在可以修改
			SELECT factor into v_oriText FROM annotate WHERE id = v_antId;
		  SELECT '指标' INTO v_attrName;
			UPDATE annotate SET factor = v_text WHERE id = v_antId;
			#把所有原来的product、factor组合的xpath改成现在product、factor组合
			UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
			#UPDATE annotate_xpath SET factor = v_text WHERE product = v_product AND factor = v_factor AND docId = v_docId;
			SELECT 1 INTO v_errorCode;
			#SELECT 1 INTO v_update;
		ELSEIF v_num > 0 THEN #已经存在不可以修改
			SELECT -1 INTO v_errorCode;
		END IF;
	ELSEIF v_type = 3 THEN #修改的是max属性
		SELECT max into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '最大限值' INTO v_attrName;
		UPDATE annotate SET max = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 4 THEN #修改的是min属性
		SELECT min into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '最小限值' INTO v_attrName;
		UPDATE annotate SET min = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type; 
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 5 THEN #修改的是unit属性
		SELECT unit into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '单位' INTO v_attrName;
		UPDATE annotate SET unit = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 6 THEN #修改的是adi属性
		SELECT adi into v_oriText FROM annotate WHERE id = v_antId;
		SELECT 'ADI' INTO v_attrName;
		UPDATE annotate SET adi = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type; 
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 7 THEN #修改的是adiWeb属性
		SELECT adiWeb into v_oriText FROM annotate WHERE id = v_antId;
		SELECT 'AdiWeb' INTO v_attrName;
		UPDATE annotate SET adiWeb = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 8 THEN #修改的是cns属性
		SELECT cns into v_oriText FROM annotate WHERE id = v_antId;
		SELECT 'CNS' INTO v_attrName;
		UPDATE annotate SET cns = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 9 THEN #修改的是ins属性
		SELECT ins into v_oriText FROM annotate WHERE id = v_antId;
		SELECT 'INS' INTO v_attrName;
		UPDATE annotate SET ins = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 10 THEN #修改的是cas属性
		SELECT cas into v_oriText FROM annotate WHERE id = v_antId;
		SELECT 'CAS' INTO v_attrName;
		UPDATE annotate SET cas = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 11 THEN #修改的是struc属性
		SELECT struc into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '结构方程式' INTO v_attrName;
		UPDATE annotate SET struc = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 12 THEN #修改的是mole属性
		SELECT mole into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '摩尔方程式' INTO v_attrName;
		UPDATE annotate SET mole = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 13 THEN #修改的是property属性
		SELECT property into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '属性备注' INTO v_attrName;
		UPDATE annotate SET property = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 14 THEN #修改的是toxico属性
		SELECT toxico into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '毒理学指标' INTO v_attrName;
		UPDATE annotate SET toxico = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 15 THEN #修改的是biological属性
		SELECT biological into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '生物学指标' INTO v_attrName;
		UPDATE annotate SET biological = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 16 THEN #修改的是funct属性
		SELECT funct into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '功能' INTO v_attrName;
		UPDATE annotate SET funct = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 17 THEN #修改的是disease属性
		SELECT disease into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '病理学指标' INTO v_attrName;
		UPDATE annotate SET disease = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 18 THEN #修改的是proremark属性
		SELECT proremark into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '产品备注' INTO v_attrName;
		UPDATE annotate SET proremark = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 19 THEN #修改的是facremark属性
		SELECT facremark into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '物质备注' INTO v_attrName;
		UPDATE annotate SET facremark = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 20 THEN #修改的是prostd属性
		SELECT prostd into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '产品标准' INTO v_attrName;
		UPDATE annotate SET prostd = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	ELSEIF v_type = 21 THEN #修改的是testsId属性
		SELECT testsId into v_oriText FROM annotate WHERE id = v_antId;
		SELECT '检测标准' INTO v_attrName;
		UPDATE annotate SET testsId = v_text WHERE id = v_antId;
		UPDATE annotate_xpath SET updatedText = v_text, updated = 1 WHERE product = v_product AND factor = v_factor AND docId = v_docId AND type = v_type;
		SELECT 1 INTO v_errorCode;
	END IF;

	IF v_update = 0 AND v_flag > 0 THEN
		
		IF v_grade = 2 THEN
			SELECT '，审核=[二级审核]' INTO v_checkGrade;
		END IF;

		IF v_oriText != '' AND v_text != '' THEN
			SELECT CONCAT('{[',v_attrName,']属性修改；[',v_oriText,']修改为[',v_text,']',v_checkGrade,'，用户=[',v_user,']，时间=[',NOW(),']}') INTO v_description;
		END IF;
		IF v_oriText != '' AND v_text = '' THEN
			SELECT CONCAT('{[',v_attrName,']属性删除；被删除的值[',v_oriText,']',v_checkGrade,'，用户=[',v_user,']，时间=[',NOW(),']}') INTO v_description;
		END IF;
		IF v_oriText = '' AND v_text != '' THEN
			SELECT CONCAT('{[',v_attrName,']属性添加；',v_attrName,'=[',v_text,']',v_checkGrade,'，用户=[',v_user,']，时间=[',NOW(),']}') INTO v_description;
		END IF;

		IF v_oriText IS NULL AND v_text != '' THEN
			SELECT CONCAT('{[',v_attrName,']属性修改；[',v_oriText,']修改为[',v_text,']',v_checkGrade,'，用户=[',v_user,']，时间=[',NOW(),']}') INTO v_description;
		END IF;
		IF v_oriText IS NULL AND v_text = '' THEN
			SELECT CONCAT('{[',v_attrName,']属性删除；被删除的值[',v_oriText,']',v_checkGrade,'，用户=[',v_user,']，时间=[',NOW(),']}') INTO v_description;
		END IF;
		IF v_oriText IS NULL AND v_text != '' THEN
			SELECT CONCAT('{[',v_attrName,']属性添加；',v_attrName,'=[',v_text,']',v_checkGrade,'，用户=[',v_user,']，时间=[',NOW(),']}') INTO v_description;
		END IF;

		INSERT INTO `annotate_check`(`docId`, `antId`, `createUser`, `createTime`, `operation`, `description`, `grade`) VALUES(v_did, v_antId, v_user, NOW(), 3, v_description, v_grade)
		ON DUPLICATE KEY UPDATE `description` = CONCAT(IFNULL(`description`, ''), v_description);
	END IF;

	SELECT v_errorCode;

END
;;
DELIMITER ;
