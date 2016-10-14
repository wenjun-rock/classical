package fwj.classical.mozart.forex.init;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.io.Files;

import fwj.classical.mozart.forex.price.entity.ForexPrice;
import fwj.classical.mozart.forex.price.repos.ForexPriceRepos;
import fwj.classical.mozart.manual.launch.AbstractBaseLaunch;

/**
 * 
 * 十大货币交易量： 美元（85%）、欧元（39%）、日元（19%）、英镑（13%）、澳元（8%）、
 * 瑞郎（6%）、加元（5%）、墨西哥比索（2.5%）、人民币（2.2%）、纽元（1.4%）。
 * 
 */
@Component
public class ForexWaveFold extends AbstractBaseLaunch {

	@Autowired
	private ForexPriceRepos forexPriceRepos;

	@Override
	protected void execute() throws Exception {
		DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
		Date startDt = yyyyMMdd.parse("2015-01-01");
		Date endDt = yyyyMMdd.parse("2015-12-31");

		List<WavePrice> waveList = new ArrayList<>();

		List<ForexPrice> priceList = forexPriceRepos.findByCodeAndDtBetweenOrderByDtAsc("EURUSD", startDt, endDt);
		for (int i = 0; i < priceList.size(); i++) {
			ForexPrice curr = priceList.get(i);
			if (i == 0) {
				waveList.add(new WavePrice(curr.getDt(), curr.getOpen()));
			} else if (i == priceList.size() - 1) {
				waveList.add(new WavePrice(curr.getDt(), curr.getClose()));
			} else {
				ForexPrice prev = priceList.get(i-1);
				ForexPrice next = priceList.get(i+1);
				int max1 = curr.getMax().compareTo(prev.getMax());
				int max2 = curr.getMax().compareTo(next.getMax());
				boolean h = max1 >=0 && max2 >= 0 && (max1 + max2) > 0;
				int min1 = curr.getMin().compareTo(prev.getMin());
				int min2 = curr.getMin().compareTo(next.getMin());
				boolean l = min1 <= 0 && min2 <= 0 && (min1 + min2) < 0;
				if(h && !l) {
					waveList.add(new WavePrice(curr.getDt(), curr.getMax()));
				} else if (l && !h) {
					waveList.add(new WavePrice(curr.getDt(), curr.getMin()));
				}
			}
		}
		
		
		List<String> dataList = new ArrayList<>();
		dataList.add("raw_d,raw_min,raw_max,summit_d,summit_p,");
		for (int i = 0; i < priceList.size(); i++) {
			StringBuilder sb = new StringBuilder();
			ForexPrice forexPrice = priceList.get(i);
			sb.append(yyyyMMdd.format(forexPrice.getDt()));
			sb.append(",");
			sb.append(forexPrice.getMin());
			sb.append(",");
			sb.append(forexPrice.getMax());
			sb.append(",");
			if(waveList.size() > i) {
				WavePrice wavePrice = waveList.get(i);
				sb.append(yyyyMMdd.format(wavePrice.getD()));
				sb.append(",");
				sb.append(wavePrice.getP());
				sb.append(",");
			}
			dataList.add(sb.toString());
		}
		
		Files.asCharSink(new File("F:/201610/1009.csv"), StandardCharsets.UTF_8).writeLines(dataList);

	}

	public static void main(String[] args) {
		launch(ForexWaveFold.class);
	}

	public static class WavePrice {
		private Date d;
		private BigDecimal p;

		public WavePrice(Date d, BigDecimal p) {
			this.d = d;
			this.p = p;
		}

		public Date getD() {
			return d;
		}

		public BigDecimal getP() {
			return p;
		}

	}

}
