package com.example.pc.doantotnghiep.Controller;

import com.example.pc.doantotnghiep.Model.ThanhVienModel;

/**
 * Created by PC on 21/03/2018.
 */

public class DangKyController {
    ThanhVienModel thanhVienModel;

    public DangKyController() {
        thanhVienModel = new ThanhVienModel();
    }

    public void ThemThongTinThanhVienController(ThanhVienModel thanhVienModel, String uid) {
        thanhVienModel.ThemThongTinThanhVien(thanhVienModel,uid);
    }
}
