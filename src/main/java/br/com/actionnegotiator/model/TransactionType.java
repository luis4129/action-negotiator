package br.com.actionnegotiator.model;

import java.math.BigDecimal;

public enum TransactionType {

	NULL {
		@Override
		public BigDecimal getValue(InvestmentRule investmentRule) {
			return null;
		}
	},

	PURCHASE {
		@Override
		public String toString() {
			return "Compra";			
		}
		
		@Override
		public BigDecimal getValue(InvestmentRule investmentRule) {
			return investmentRule.getPurchasePrice();
		}
	},

	SALE {
		@Override
		public String toString() {
			return "Venda";			
		}
		
		@Override
		public BigDecimal getValue(InvestmentRule investmentRule) {
			return investmentRule.getSalePrice();
		}
	};

	public abstract BigDecimal getValue(InvestmentRule investmentRule);

}
