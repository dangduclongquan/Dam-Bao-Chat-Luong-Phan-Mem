package com.example.dbclpm.nhapdiemtinhdiem.dao;

import com.example.dbclpm.nhapdiemtinhdiem.model.Diem;

import java.sql.SQLException;
import java.util.List;

public interface IDiemDAO {
    public void CreateDiem(Diem diem) throws SQLException;
    public List<Diem> ReadDiem(int lopId) throws SQLException;
    public void UpdateDiem(Diem diem) throws SQLException;
    public void DeleteDiem(Diem diem) throws SQLException;
}
