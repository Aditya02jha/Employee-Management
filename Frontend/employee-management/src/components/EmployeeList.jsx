import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";

const EmployeeList = () => {
  const [employees, setEmployees] = useState([]);
  const [countries, setCountries] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [filters, setFilters] = useState({ countryId: "", departmentId: "" });

  useEffect(() => {
    fetchEmployees();
    fetchCountries();
    fetchDepartments();
  }, []);

  useEffect(() => {
    fetchEmployees();
  }, [filters]);

  const fetchEmployees = async () => {
    try {
      const queryParams = new URLSearchParams(filters).toString();
      const res = await axios.get(`${API_URL}/api/employee/employees?${queryParams}`);
      setEmployees(res.data);
    } catch (error) {
      console.error("Error fetching employees", error);
    }
  };

  const fetchCountries = async () => {
    try {
      const res = await axios.get(`${API_URL}/api/countries`);
      setCountries(res.data);
    } catch (error) {
      console.error("Error fetching countries", error);
    }
  };

  const fetchDepartments = async () => {
    try {
      const res = await axios.get(`${API_URL}/api/departments`);
      setDepartments(res.data);
    } catch (error) {
      console.error("Error fetching departments", error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`${API_URL}/api/employee/${id}`);
      fetchEmployees();
    } catch (error) {
      console.error("Error deleting employee", error);
    }
  };

  return (
    <div className="min-h-screen bg-black text-white flex flex-col p-6">
      <h1 className="text-3xl font-semibold text-center mb-6">Employee List</h1>

      {/* Filters Section */}
      <div className="flex flex-wrap justify-center gap-4 mb-6">
        <select
          className="bg-gray-800 text-white p-3 rounded-md border border-gray-700"
          value={filters.countryId}
          onChange={(e) => setFilters((prev) => ({ ...prev, countryId: e.target.value }))}
        >
          <option value="">All Countries</option>
          {countries.map((country) => (
            <option key={country.id} value={country.id}>
              {country.name}
            </option>
          ))}
        </select>

        <select
          className="bg-gray-800 text-white p-3 rounded-md border border-gray-700"
          value={filters.departmentId}
          onChange={(e) => setFilters((prev) => ({ ...prev, departmentId: e.target.value }))}
        >
          <option value="">All Departments</option>
          {departments.map((dept) => (
            <option key={dept.id} value={dept.id}>
              {dept.name}
            </option>
          ))}
        </select>
      </div>

      {/* Employee Table */}
      <div className="overflow-x-auto">
        <table className="w-full text-left border border-gray-700">
          <thead>
            <tr className="bg-gray-800">
              <th className="p-3 border border-gray-700">ID</th>
              <th className="p-3 border border-gray-700">Name</th>
              <th className="p-3 border border-gray-700">Email</th>
              <th className="p-3 border border-gray-700">Country</th>
              <th className="p-3 border border-gray-700">Departments</th>
              <th className="p-3 border border-gray-700">Actions</th>
            </tr>
          </thead>
          <tbody>
            {employees.length > 0 ? (
              employees.map((emp) => (
                <tr key={emp.id} className="hover:bg-gray-800">
                  <td className="p-3 border border-gray-700">{emp.id}</td>
                  <td className="p-3 border border-gray-700">{emp.name}</td>
                  <td className="p-3 border border-gray-700">{emp.email}</td>
                  <td className="p-3 border border-gray-700">{emp.country?.name || "N/A"}</td>
                  <td className="p-3 border border-gray-700">
                    {emp.employeeDepartmentList?.map((d) => d.department.name).join(", ") || "N/A"}
                  </td>
                  <td className="p-3 border border-gray-700 flex gap-2">
                    <Link to={`/employee/${emp.id}`} className="bg-blue-500 hover:bg-blue-600 px-3 py-1 rounded text-white">View</Link>
                    <Link to={`/edit/${emp.id}`} className="bg-green-500 hover:bg-green-600 px-3 py-1 rounded text-white">Edit</Link>
                    <button
                      onClick={() => handleDelete(emp.id)}
                      className="bg-red-600 hover:bg-red-700 px-3 py-1 rounded text-white"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="6" className="text-center p-4">
                  No employees found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default EmployeeList;
