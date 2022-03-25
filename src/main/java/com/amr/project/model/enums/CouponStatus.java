package com.amr.project.model.enums;

public enum CouponStatus {

    ACTUAL {
        @Override
        public String statusDescription() {
            return "Купон действительный";
        }
    },

    USED {
        @Override
        public String statusDescription(){return "Купон использован";}
    },

    OVERDUE {
        @Override
        public String statusDescription(){return "Купон просрочен";}
    };

    public abstract String statusDescription();
}
