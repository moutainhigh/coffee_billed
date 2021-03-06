package cn.sisyphe.coffee.bill.domain.plan.strategy;

import cn.sisyphe.coffee.bill.domain.base.model.enums.BillTypeEnum;
import cn.sisyphe.coffee.bill.domain.base.model.enums.StationType;
import cn.sisyphe.coffee.bill.domain.base.model.location.AbstractLocation;
import cn.sisyphe.coffee.bill.domain.base.model.location.Station;
import cn.sisyphe.coffee.bill.domain.plan.PlanBill;
import cn.sisyphe.coffee.bill.domain.plan.payload.PlanBillPayload;
import cn.sisyphe.coffee.bill.infrastructure.base.BillRepository;
import cn.sisyphe.framework.web.exception.DataException;
import org.springframework.stereotype.Service;

import static cn.sisyphe.coffee.bill.domain.base.model.enums.BillPurposeEnum.OutStorage;
import static cn.sisyphe.coffee.bill.domain.base.model.enums.BillTypeEnum.RETURNED;


/**
 * @author ncmao
 * 退货计划单策略
 */

@Service
public class ReturnedStrategy extends AbstractCastableStrategy {


    @SuppressWarnings("unchecked")
    @Override
    public void cast(PlanBillPayload planBillPayload, BillRepository billRepository) {
        //出站点为物流则直接生成退货单
        AbstractLocation outLocation = planBillPayload.getOutLocation();
        if (outLocation instanceof Station && StationType.LOGISTICS.equals(((Station) outLocation).getStationType())) {
            PlanBill returnedBill = generatePlanBill(planBillPayload, RETURNED, OutStorage);
            billRepository.save(returnedBill);
            return;
        }
        //出站点为门店则先生成退库计划，再生成退货计划
        if (outLocation instanceof Station && StationType.STORE.equals(((Station) outLocation).getStationType())) {
            PlanBill restockBill = generatePlanBill(planBillPayload, BillTypeEnum.RESTOCK, OutStorage);
            billRepository.save(restockBill);

            PlanBill returnedBill = generatePlanBill(planBillPayload, BillTypeEnum.RETURNED, OutStorage);
            billRepository.save(returnedBill);
        }
        throw new DataException("123456", "站点选择有错误!");
    }
}
