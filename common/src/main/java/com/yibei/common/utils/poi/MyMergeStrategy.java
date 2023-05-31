package com.yibei.common.utils.poi;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

/**
 * 自定义的合并策略,参考官方文档的LoopMergeStrategy
 */
public class MyMergeStrategy extends AbstractMergeStrategy {
    //合并坐标集合
    private List<CellRangeAddress> cellRangeAddresss;

    //构造
    public MyMergeStrategy(List<CellRangeAddress> cellRangeAddresss) {
        this.cellRangeAddresss = cellRangeAddresss;
    }

    /**
     * merge
     *
     * @param sheet
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        //合并单元格
        /**
         *  ****加个判断:if (cell.getRowIndex() == 1 && cell.getColumnIndex() == 0) {}****
         * 保证每个cell被合并一次，如果不加上面的判断，因为是一个cell一个cell操作的，
         * 例如合并A2:A3,当cell为A2时，合并A2,A3，但是当cell为A3时，又是合并A2,A3，
         * 但此时A2,A3已经是合并的单元格了
         */

        if (CollectionUtils.isNotEmpty(cellRangeAddresss)) {
            if (cell.getRowIndex() == 1 && cell.getColumnIndex() == 0) {
                for (CellRangeAddress item : cellRangeAddresss) {
                    sheet.addMergedRegionUnsafe(item);
                }
            }
        }
    }
}
