package fwj.classical.mozart.resource.trade.vo;

public class TradeGroupAssignReq {

	private Integer groupId;
	private Integer[] orderIds;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer[] getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(Integer[] orderIds) {
		this.orderIds = orderIds;
	}
}
