import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";

const EmployeeForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [employeeData, setEmployeeData] = useState({
    name: "",
    email: "",
    dateOfBirth: "",
    managerId: "",
    countryId: "",
    departmentIds: [],
    addresses: [{ addressLine: "", city: "", state: "", countryId: "", zipCode: "", isCurrent: false }],
  });

  const [countries, setCountries] = useState([]);
  const [managers, setManagers] = useState([]);
  const [departments, setDepartments] = useState([]);

  useEffect(() => {
    axios.get(`${API_URL}/api/countries`).then((res) => setCountries(res.data));
    axios.get(`${API_URL}/api/managers`).then((res) => setManagers(res.data));
    axios.get(`${API_URL}/api/departments`).then((res) => setDepartments(res.data));

    if (id) {
      axios.get(`${API_URL}/api/employee/${id}`).then((res) => {
        const emp = res.data;
        setEmployeeData({
          name: emp.name,
          email: emp.email,
          dateOfBirth: emp.dateOfBirth ? emp.dateOfBirth.split("T")[0] : "",
          managerId: emp.manager ? emp.manager.id : "",
          countryId: emp.country ? emp.country.id : "",
          departmentIds: emp.employeeDepartmentList ? emp.employeeDepartmentList.map((d) => d.department.id) : [],
          addresses: emp.addresses.length > 0 ? emp.addresses : [{ addressLine: "", city: "", state: "", countryId: "", zipCode: "", isCurrent: false }],
        });
      });
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEmployeeData((prev) => ({ ...prev, [name]: value }));
  };

  const handleDepartmentChange = (e) => {
    const selectedDepartmentId = Number(e.target.value);
    if (!employeeData.departmentIds.includes(selectedDepartmentId)) {
      setEmployeeData(prev => ({
        ...prev,
        departmentIds: [...prev.departmentIds, selectedDepartmentId]
      }));
    }
  };

  const removeDepartment = (idToRemove) => {
    setEmployeeData(prev => ({
      ...prev,
      departmentIds: prev.departmentIds.filter(id => id !== idToRemove)
    }));
  };

  const handleAddressChange = (index, e) => {
    const { name, value, type, checked } = e.target;
    const addresses = [...employeeData.addresses];
    addresses[index][name] = type === "checkbox" ? checked : value;
    setEmployeeData((prev) => ({ ...prev, addresses }));
  };

  const addAddress = () => {
    setEmployeeData((prev) => ({
      ...prev,
      addresses: [...prev.addresses, { addressLine: "", city: "", state: "", countryId: "", zipCode: "", isCurrent: false }],
    }));
  };

  const removeAddress = (index) => {
    if (employeeData.addresses.length > 1) {
      const addresses = [...employeeData.addresses];
      addresses.splice(index, 1);
      setEmployeeData((prev) => ({ ...prev, addresses }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formattedPayload = {
      name: employeeData.name,
      email: employeeData.email,
      dateOfBirth: employeeData.dateOfBirth, 
      managerId: employeeData.managerId ? Number(employeeData.managerId) : null,
      countryId: Number(employeeData.countryId),
      departmentIds: employeeData.departmentIds,
      addresses: employeeData.addresses.map((addr) => ({
        addressLine: addr.addressLine,
        city: addr.city,
        state: addr.state,
        countryId: Number(addr.countryId),
        zipCode: addr.zipCode,
        isCurrent: addr.isCurrent,
      })),
    };

    try {
      if (id) {
        await axios.put(`${API_URL}/api/employee/${id}`, formattedPayload);
      } else {
        await axios.post(`${API_URL}/api/admin/addEmployee`, formattedPayload);
      }
      navigate("/");
    } catch (error) {
      console.error("Error saving employee", error);
    }
  };

  return (
    <div className="min-h-screen bg-black text-white flex justify-center items-center p-6">
      <div className="w-full max-w-4xl bg-gray-900 p-8 rounded-lg shadow-md">
        <h1 className="text-3xl font-semibold text-center mb-6">{id ? "Edit Employee" : "Add Employee"}</h1>
        <form onSubmit={handleSubmit} className="space-y-4">
          
          {/* Employee Details */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <input type="text" name="name" placeholder="Name" value={employeeData.name} onChange={handleChange} required className="bg-gray-800 text-white p-3 rounded-md border border-gray-700" />
            <input type="email" name="email" placeholder="Email" value={employeeData.email} onChange={handleChange} required className="bg-gray-800 text-white p-3 rounded-md border border-gray-700" />
          </div>

          {/* Date of Birth & Manager */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <input type="date" name="dateOfBirth" value={employeeData.dateOfBirth} onChange={handleChange} required className="bg-gray-800 text-white p-3 rounded-md border border-gray-700" />
            <select name="managerId" value={employeeData.managerId} onChange={handleChange} className="bg-gray-800 text-white p-3 rounded-md border border-gray-700">
              <option value="">Select Manager</option>
              {managers.map((manager) => <option key={manager.id} value={manager.id}>{manager.name}</option>)}
            </select>
          </div>

          {/* Country Selection */}
          <select name="countryId" value={employeeData.countryId} onChange={handleChange} required className="bg-gray-800 text-white p-3 rounded-md border border-gray-700 w-full">
            <option value="">Select Country</option>
            {countries.map((country) => <option key={country.id} value={country.id}>{country.name}</option>)}
          </select>

          {/* Address Section */}
          <h3 className="text-lg font-semibold mb-2">Addresses</h3>
          {employeeData.addresses.map((addr, index) => (
            <div key={index} className="p-4 mb-3 border border-gray-700 rounded-lg bg-gray-800">
              <input type="text" name="addressLine" placeholder="Address Line" value={addr.addressLine} onChange={(e) => handleAddressChange(index, e)} required className="bg-gray-700 text-white p-2 rounded-md border border-gray-600 w-full"/>
              <input type="text" name="city" placeholder="City" value={addr.city} onChange={(e) => handleAddressChange(index, e)} required className="bg-gray-700 text-white p-2 rounded-md border border-gray-600 w-full"/>
              <input type="text" name="state" placeholder="State" value={addr.state} onChange={(e) => handleAddressChange(index, e)} required className="bg-gray-700 text-white p-2 rounded-md border border-gray-600 w-full"/>
              <button type="button" onClick={() => removeAddress(index)} className="mt-2 p-2 bg-red-600 hover:bg-red-500 text-white rounded-md transition">Remove Address</button>
            </div>
          ))}
          <button type="submit" className="w-full p-3 bg-gray-700 text-white rounded-lg hover:bg-green-500">Save Employee</button>
        </form>
      </div>
    </div>
  );
};

export default EmployeeForm;
