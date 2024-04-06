package com.example.dbclpm.nhapdiemtinhdiem;

import com.example.dbclpm.nhapdiemtinhdiem.dao.impl.DiemDAO;
import com.example.dbclpm.nhapdiemtinhdiem.model.Diem;
import com.example.dbclpm.nhapdiemtinhdiem.model.Lop;

import java.io.InvalidObjectException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhapDiemTinhDiem {
    public DiemDAO diemDAO= new DiemDAO();

    public List<Lop> GetLopList(int giangVienId, Date thoiGian) throws SQLException
    {
        List<Lop> lopList = diemDAO.ReadLopListOfGiangVien(giangVienId);
        List<Lop> lopListOfDate = new ArrayList<>();
        for(Lop lop: lopList)
        {
            if(thoiGian.after(lop.hocKi.batDau) && thoiGian.before(lop.hocKi.ketThuc))
            {
                lopListOfDate.add(lop);
            }
        }
        return lopListOfDate;
    }
    public List<Lop> GetLopList(int giangVienId) throws SQLException
    {
        return diemDAO.ReadLopListOfGiangVien(giangVienId);
    }

    public List<Diem> GetDiemList(int lopId) throws SQLException
    {
        return diemDAO.ReadDiem(lopId);
    }

    public void UpdateDiem(Diem diem) throws SQLException, InvalidObjectException
    {
        if(diem.chuyenCan == null || (diem.chuyenCan>=0 && diem.chuyenCan <=10)) {}
        else throw new InvalidObjectException("Diem chuyen can phai nam trong khoang tu 0 den 10.");
        if(diem.thucHanh == null || (diem.thucHanh>=0 && diem.thucHanh <=10)) {}
        else throw new InvalidObjectException("Diem thuc hanh phai nam trong khoang tu 0 den 10.");
        if(diem.baiTapLon==null || (diem.baiTapLon>=0 && diem.baiTapLon <=10)) {}
        else throw new InvalidObjectException("Diem bai tap lon phai nam trong khoang tu 0 den 10.");
        if(diem.kiemTraGiuaKi==null || (diem.kiemTraGiuaKi>=0 && diem.kiemTraGiuaKi <=10)) {}
        else throw new InvalidObjectException("Diem kiem tra giua ki phai nam trong khoang tu 0 den 10.");
        if(diem.kiemTraCuoiKi==null || (diem.kiemTraCuoiKi>=0 && diem.kiemTraCuoiKi <=10)) {}
        else throw new InvalidObjectException("Diem kiem tra cuoi ki phai nam trong khoang tu 0 den 10.");
        diemDAO.UpdateDiem(diem);
    }

    public static Float CalculateDiemTongKet(Diem diem)
    {
        return diem.chuyenCan*diem.lop.mon.pt_cc/100+
                diem.thucHanh*diem.lop.mon.pt_th/100+
                diem.baiTapLon*diem.lop.mon.pt_btl/100+
                diem.kiemTraGiuaKi*diem.lop.mon.pt_ktgk/100+
                diem.kiemTraCuoiKi*diem.lop.mon.pt_ktck/100;
    }

    public static String DoiSangDiemChu(Float diem)
    {
        if(diem==null) return null;
        if(diem>=9.0) return "A+";
        if(diem>=8.5) return "A";
        if(diem>=8.0) return "B+";
        if(diem>=7.0) return "B";
        if(diem>=6.5) return "C+";
        if(diem>=5.5) return "C";
        if(diem>=5.0) return "D+";
        if(diem>=4.0) return "D";
        return "F";
    }
}
