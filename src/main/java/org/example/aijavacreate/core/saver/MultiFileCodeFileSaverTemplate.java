package org.example.aijavacreate.core.saver;

import cn.hutool.core.util.StrUtil;
import org.example.aijavacreate.ai.model.MultiFileCodeResult;
import org.example.aijavacreate.exception.BusinessException;
import org.example.aijavacreate.exception.ErrorCode;
import org.example.aijavacreate.model.enums.CodeGenTypeEnum;

/**
 * 多文件代码保存器
 *
 * @author penguin
 */
public class MultiFileCodeFileSaverTemplate extends CodeFileSaverTemplate<MultiFileCodeResult> {
    @Override
    public CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.MULTI_FILE;
    }
    /**
     * 保存多文件代码
     *
     * @param result      代码结果对象
     * @param baseDirPath 基础目录路径
     */
    @Override
    protected void saveFiles(MultiFileCodeResult result, String baseDirPath) {
        // 保存 HTML 文件
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
        // 保存 CSS 文件
        writeToFile(baseDirPath, "style.css", result.getCssCode());
        // 保存 JavaScript 文件
        writeToFile(baseDirPath, "script.js", result.getJsCode());
    }
    /**
     * 验证输入参数
     *
     * @param result 代码结果对象
     * @throws BusinessException 如果 HTML 代码为空则抛出异常
     */
    @Override
    protected void validateInput(MultiFileCodeResult result) {
        super.validateInput(result);
        // 至少要有 HTML 代码，CSS 和 JS 可以为空
        if (StrUtil.isBlank(result.getHtmlCode())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "HTML代码内容不能为空");
        }
    }
}
