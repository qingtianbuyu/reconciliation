package com.payment.reconciliation.engine;

import com.payment.reconciliation.engine.model.BankVo;
import com.payment.reconciliation.engine.model.DiffType;
import com.payment.reconciliation.engine.model.MergeVo;
import com.payment.reconciliation.engine.model.PayOrgVo;
import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.RichGroupReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.CoGroupOperator;
import org.apache.flink.api.java.operators.GroupReduceOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;


public class CheckCoreService {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().enableForceKryo();
        // 加载本地数据
        DataSet<PayOrgVo> payOrgSource = env.fromElements(new PayOrgVo("113", 1), new PayOrgVo("000", 2), new PayOrgVo("115", 33), new PayOrgVo("1159", 33));
        // 加载支付渠道数据
        DataSet<BankVo> bankSource = env.fromElements(new BankVo("000", 2), new BankVo("115", 333), new BankVo("114", 4));

        CoGroupOperator<PayOrgVo, BankVo, MergeVo> merge = payOrgSource.coGroup(bankSource)
                .where(PayOrgVo::getOrderNo)
                .equalTo(BankVo::getOrderNo)
                .with(new CoGroupFunction<PayOrgVo, BankVo, MergeVo>() {
                    @Override
                    public void coGroup(Iterable<PayOrgVo> first, Iterable<BankVo> second, Collector<MergeVo> out) throws Exception {
                        PayOrgVo payOrgVo = null;
                        BankVo bankVo = null;

                        for (PayOrgVo vo : first) {
                            payOrgVo = vo;
                        }

                        for (BankVo vo : second) {
                            bankVo = vo;
                        }

                        // 返回数据
                        out.collect(new MergeVo(getDiffType(payOrgVo, bankVo), payOrgVo, bankVo));
                    }
                });

        // 分组
        GroupReduceOperator<MergeVo, Tuple2<DiffType, List<MergeVo>>> diffType = merge.groupBy("diffType")
                .reduceGroup(new RichGroupReduceFunction<MergeVo, Tuple2<DiffType, List<MergeVo>>>() {
                    @Override
                    public void reduce(Iterable<MergeVo> values, Collector<Tuple2<DiffType, List<MergeVo>>> out) throws Exception {
                        List<MergeVo> mergeVoList = new ArrayList<>();
                        DiffType diffType = null;
                        for (MergeVo mergeVo : values) {
                            diffType = mergeVo.getDiffType();
                            mergeVoList.add(mergeVo);
                        }
                        out.collect(Tuple2.of(diffType, mergeVoList));
                    }
                });

        diffType.print();

//        env.execute("merge");

    }

    /**
     * 校验对比
     *
     * @param payOrgVo
     * @param bankVo
     * @return
     */
    private static DiffType getDiffType(PayOrgVo payOrgVo, BankVo bankVo) {
        // 相同orderNo下，支付机构有数据
        if (bankVo == null) {
            return DiffType.SRC_MORE;
        }
        // 银行有数据
        if (payOrgVo == null) {
            return DiffType.TARGET_MORE;
        }
        // 数据完全一致
        if (payOrgVo.getPayment().equals(bankVo.getPayment())) {
            return DiffType.MATCH;
        }
        // orderNo相同但payment不同
        return DiffType.MIS;
    }
}
