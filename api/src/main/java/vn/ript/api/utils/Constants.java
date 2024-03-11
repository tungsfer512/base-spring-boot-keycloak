package vn.ript.api.utils;

import java.util.HashMap;

public class Constants {

    private static HashMap<Integer, LOI> LOI_MAP = new HashMap<>();

    public enum LOI {
        THIEU_THONG_TIN(400, "Thieu thong tin"),
        KHONG_TIM_THAY(404, "Khong tim thay doi tuong can tim"),
        ;

        private final Integer ma;
        private final String moTa;

        private LOI(final Integer ma, final String moTa) {
            this.ma = ma;
            this.moTa = moTa;
            LOI_MAP.put(ma, this);
        }

        public Integer ma() {
            return this.ma;
        }

        public String moTa() {
            return this.moTa;
        }

        public LOI getByMa(Integer ma) {
            return LOI_MAP.get(ma);
        }
    }

}
