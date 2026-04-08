package org.example.aijavacreate.core.saver;

import cn.hutool.core.util.StrUtil;
import org.example.aijavacreate.ai.model.HtmlCodeResult;
import org.example.aijavacreate.exception.BusinessException;
import org.example.aijavacreate.exception.ErrorCode;
import org.example.aijavacreate.model.enums.CodeGenTypeEnum;

/**
 * HTML代码文件保存器
 *
 * @author penguin
 */
public class HtmlCodeFileSaverTemplate extends CodeFileSaverTemplate<HtmlCodeResult> {

    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.HTML;
    }

    /**
     * 保存 HTML 代码
     *
     * @param result  代码结果对象
     * @param baseDirPath 基础目录路径
     */
    @Override
    protected void saveFiles(HtmlCodeResult result, String baseDirPath) {
        // 保存 HTML 文件
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
    }

    /**
     * 验证 HTML 代码
     *
     * @param result 代码结果对象
     * @throws BusinessException 如果 HTML 代码为空则抛出异常
     */
    @Override
    protected void validateInput(HtmlCodeResult result) {
        super.validateInput(result);
        // HTML 代码不能为空
        if (StrUtil.isBlank(result.getHtmlCode())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "HTML代码内容不能为空");
        }
    }
}
