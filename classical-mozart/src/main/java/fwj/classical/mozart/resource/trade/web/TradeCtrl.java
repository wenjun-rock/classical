package fwj.classical.mozart.resource.trade.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fwj.classical.mozart.resource.trade.buss.TradeOrderBuss;
import fwj.classical.mozart.resource.trade.vo.TradeGroupAssignReq;
import fwj.classical.mozart.resource.trade.vo.TradeGroupView;
import fwj.classical.mozart.resource.trade.vo.TradeOrderAssignReq;
import fwj.classical.mozart.resource.trade.vo.TradeOrderView;

@RestController()
@RequestMapping("/trade")
public class TradeCtrl {

	@Autowired
	private TradeOrderBuss tradeOrderBuss;
	
	@RequestMapping(value = "/upload-trade-order", method = RequestMethod.POST)
	public int uploadTradeOrder(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
            try {
            	return tradeOrderBuss.saveTradeOrderInExcel(file.getBytes());
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
		return 0;
	}
	
	@RequestMapping(value = "/list-trade-order", method = RequestMethod.GET)
	public List<TradeOrderView> listTradeOrder() {
		return tradeOrderBuss.listTradeOrder();
	}
	
	@RequestMapping(value = "/save-trade-group", method = RequestMethod.POST)
	public TradeGroupView addTradeGroup(@RequestBody TradeGroupView body) {
		return tradeOrderBuss.saveTradeGroup(body);
	}
	
	@RequestMapping(value = "/assign-trade-group", method = RequestMethod.POST)
	public TradeOrderView assignTradeGroup(@RequestBody TradeGroupAssignReq body) {
		tradeOrderBuss.assignTradeGroup(body);
		return new TradeOrderView();
	}
	
	@RequestMapping(value = "/assign-trade-order", method = RequestMethod.POST)
	public TradeGroupView assignTradeOrder(@RequestBody TradeOrderAssignReq body) {
		tradeOrderBuss.assignTradeOrder(body);
		return new TradeGroupView();
	}
	
	@RequestMapping(value = "/list-trade-group", method = RequestMethod.GET)
	public List<TradeGroupView> listTradeGroup(@RequestParam(value = "order", defaultValue = "true") boolean orderFlag) {
		return tradeOrderBuss.listTradeGroup(orderFlag);
	}

}
