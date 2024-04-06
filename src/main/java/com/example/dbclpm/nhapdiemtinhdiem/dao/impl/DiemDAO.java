package com.example.dbclpm.nhapdiemtinhdiem.dao.impl;

import com.example.dbclpm.nhapdiemtinhdiem.dao.IDiemDAO;
import com.example.dbclpm.nhapdiemtinhdiem.model.*;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiemDAO implements IDiemDAO {
    HikariDataSource hikariDataSource;
    public DiemDAO() {
        hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/dbclpm");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("amogus");
    }

    @Override
    public void CreateDiem(Diem diem) throws SQLException {

    }

    @Override
    public List<Diem> ReadDiem(int lopId) throws SQLException{
        String SQL_QUERY = "select * from point where class_id = ?";
        List<Diem> diemList;


        Connection con = hikariDataSource.getConnection();
        PreparedStatement pst = con.prepareStatement( SQL_QUERY );
        pst.setInt(1, lopId);

        ResultSet rs = pst.executeQuery();

        diemList = new ArrayList<>();
        Diem diem;
        while ( rs.next() ) {
            diem = new Diem();
            diem.id= rs.getInt( "id" );
            diem.lop= ReadLop(rs.getInt("class_id"));

            diem.sinhVien = ReadSinhVien(rs.getInt("student_id"));

            diem.chuyenCan = rs.getFloat("cc");
            if (rs.wasNull()) diem.chuyenCan=null;
            diem.baiTapLon = rs.getFloat("btl");
            if (rs.wasNull()) diem.baiTapLon=null;
            diem.kiemTraGiuaKi = rs.getFloat("ktgk");
            if (rs.wasNull()) diem.kiemTraGiuaKi=null;
            diem.kiemTraCuoiKi = rs.getFloat("ktck");
            if (rs.wasNull()) diem.kiemTraCuoiKi=null;
            diem.thucHanh=rs.getFloat("th");
            if (rs.wasNull()) diem.thucHanh=null;
            diemList.add(diem);
        }
        con.close();
        return diemList;
    }

    @Override
    public void UpdateDiem(Diem diem) throws SQLException{
        String sql = "UPDATE point SET cc = ?, btl = ?, ktgk = ?, ktck = ?, th = ? WHERE id = ?";

        Connection con = hikariDataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        if (diem.chuyenCan!=null) ps.setFloat(1, diem.chuyenCan);
        else ps.setNull(1, Types.FLOAT);
        if (diem.baiTapLon!=null) ps.setFloat(2, diem.baiTapLon);
        else ps.setNull(2, Types.FLOAT);
        if (diem.kiemTraGiuaKi!=null) ps.setFloat(3, diem.kiemTraGiuaKi);
        else ps.setNull(3, Types.FLOAT);
        if (diem.kiemTraCuoiKi!=null) ps.setFloat(4, diem.kiemTraCuoiKi);
        else ps.setNull(4, Types.FLOAT);
        if (diem.thucHanh!=null) ps.setFloat(5, diem.thucHanh);
        else ps.setNull(5, Types.FLOAT);
        ps.setInt(6, diem.id);

        ps.execute();

        con.close();
    }

    @Override
    public void DeleteDiem(Diem diem) throws SQLException{

    }


    Lop ReadLopRS(ResultSet rs) throws SQLException
    {
        Lop lop = new Lop();
        lop.id=rs.getInt("id");
        lop.ten = rs.getString("name");
        lop.tenPhong = rs.getString("room_name");

        lop.mon = ReadMon(rs.getInt("subject_id"));
        lop.hocKi = ReadHocKi(rs.getInt("term_id"));

        return lop;
    }
    public Lop ReadLop(int id) throws SQLException {
        String sql = "SELECT * FROM class WHERE id = ?";
        Lop lop = null;

        Connection con = hikariDataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            lop =ReadLopRS(rs);
        }
        con.close();
        return lop;
    }
    public List<Lop> ReadLopListOfGiangVien(int giangVienId) throws SQLException
    {
        String sql = "SELECT * FROM class WHERE teacher_id = ?";
        List<Lop> lopList = new ArrayList<>();

        Connection con = hikariDataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, giangVienId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lopList.add(ReadLopRS(rs));
        }
        con.close();
        return lopList;
    }

    public Mon ReadMon(int id) throws SQLException
    {
        String sql = "SELECT * FROM subject WHERE id = ?";
        Mon mon = null;

        Connection con = hikariDataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            mon = new Mon();
            mon.id=rs.getInt("id");
            mon.ten = rs.getString("name");
            mon.soTinChi = rs.getInt("number_of_credits");
            mon.pt_cc = rs.getInt("percent_cc");
            mon.pt_btl = rs.getInt("percent_btl");
            mon.pt_ktgk = rs.getInt("percent_ktgk");
            mon.pt_ktck = rs.getInt("percent_ktck");
            mon.pt_th=rs.getInt("percent_th");

        }
        con.close();
        return mon;
    }

    public HocKi ReadHocKi(int id) throws SQLException
    {
        String sql = "SELECT * FROM term WHERE id = ?";
        HocKi hocKi = null;

        Connection con = hikariDataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            hocKi = new HocKi();
            hocKi.id=rs.getInt("id");
            hocKi.batDau = rs.getDate("start_date");
            hocKi.ketThuc = rs.getDate("end_date");

        }
        con.close();
        return hocKi;
    }

    public SinhVien ReadSinhVien(int id) throws SQLException
    {
        String sql = "SELECT * FROM student WHERE id = ?";
        SinhVien sinhVien = null;

        Connection con = hikariDataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            sinhVien = new SinhVien();
            sinhVien.id = rs.getInt("id");
            sinhVien.ten = rs.getString("name");
            sinhVien.email = rs.getString("email");
            sinhVien.soDienThoai = rs.getString("phone");
            sinhVien.diaChi = rs.getString("address");
            sinhVien.gioiTinh=rs.getString("gender");
            sinhVien.ngaySinh=rs.getDate("date_of_birth");
            sinhVien.lopHanhChinh=rs.getString("administrative_class");
        }

        con.close();
        return sinhVien;
    }
}
