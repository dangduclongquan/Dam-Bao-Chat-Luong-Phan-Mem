package com.example.dbclpm.nhapdiemtinhdiem;

import com.example.dbclpm.nhapdiemtinhdiem.model.Diem;
import com.example.dbclpm.nhapdiemtinhdiem.model.Lop;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        try {
            NhapDiemTinhDiem nhapDiemTinhDiem = new NhapDiemTinhDiem();


            //Test list diem theo lop + update diem + doi sang diem chu
            List<Diem> diemList =nhapDiemTinhDiem.GetDiemList(23);
            diemList.get(0).chuyenCan= Float.valueOf(5);
            System.out.println(NhapDiemTinhDiem.DoiSangDiemChu(diemList.get(0).chuyenCan));

            nhapDiemTinhDiem.UpdateDiem(diemList.get(0));
            for(Diem diem : diemList)
            {
               System.out.println(diem.sinhVien.ten + ", ngay sinh: " + diem.sinhVien.ngaySinh.toString() + ", cc: " + diem.chuyenCan + ", th: " + diem.thucHanh);
            }

            //Test list lop theo giang vien
            List<Lop> lopList = nhapDiemTinhDiem.GetLopList(1);
            for(Lop lop: lopList)
            {
                System.out.println(lop.ten + ", phong:" + lop.tenPhong);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
