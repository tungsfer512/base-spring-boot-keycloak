package vn.ript.api.utils;

import java.util.HashMap;

public class Constants {

    private static HashMap<Integer, LOI> LOI_MAP = new HashMap<>();
    private static HashMap<String, LOAI_REQUEST> LOAI_REQUEST_MAP = new HashMap<>();

    public enum LOI {
        THIEU_THONG_TIN(400, "Thieu thong tin"),
        KHONG_TIM_THAY(404, "Khong tim thay doi tuong can tim"),
        SERVER_ERROR(500, "Co loi xay ra"),
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

    public enum LOAI_REQUEST {
        JSON("JSON", "Response tra ve co dang JSON"),
        FILE("FILE", "Response tra ve co dang FILE"),
        ;

        private final String ma;
        private final String moTa;

        private LOAI_REQUEST(final String ma, final String moTa) {
            this.ma = ma;
            this.moTa = moTa;
            LOAI_REQUEST_MAP.put(ma, this);
        }

        public String ma() {
            return this.ma;
        }

        public String moTa() {
            return this.moTa;
        }

        public LOAI_REQUEST getByMa(String ma) {
            return LOAI_REQUEST_MAP.get(ma);
        }
    }

}
