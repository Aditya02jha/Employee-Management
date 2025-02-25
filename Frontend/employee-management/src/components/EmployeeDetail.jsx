import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

const EmployeeDetail = () => {
  const { id } = useParams();
  const [employee, setEmployee] = useState(null);

  useEffect(() => {
    axios.get(`${API_URL}/api/employee/${id}`)
      .then(res => setEmployee(res.data))
      .catch(err => console.error("Error fetching employee details", err));
  }, [id]);

  if (!employee) {
    return <div className="text-center text-gray-500 text-lg mt-10">Loading employee details...</div>;
  }

  return (
    <div className="max-w-4xl mx-auto mt-10 p-6 bg-white shadow-lg rounded-lg">
      <h1 className="text-3xl font-bold text-gray-800 border-b pb-2 mb-4">{employee.name}</h1>
      
      {/* Employee Info */}
      <div className="mb-6">
        <p className="text-lg"><strong>Email:</strong> {employee.email}</p>
        <p className="text-lg"><strong>Date of Birth:</strong> {new Date(employee.date_of_birth).toLocaleDateString()}</p>
        <p className="text-lg"><strong>Country:</strong> {employee.country ? employee.country.name : 'N/A'}</p>
      </div>

      {/* Departments */}
      <div className="bg-gray-100 p-4 rounded-lg mb-6">
        <h2 className="text-xl font-semibold mb-2">Departments</h2>
        {employee.employeeDepartmentList?.length > 0 ? (
          <ul className="list-disc pl-5">
            {employee.employeeDepartmentList.map((dept) => (
              <li key={dept.id} className="text-gray-700">{dept.department.name}</li>
            ))}
          </ul>
        ) : (
          <p className="text-gray-500">No assigned departments.</p>
        )}
      </div>

      {/* Addresses */}
      <div className="bg-gray-100 p-4 rounded-lg mb-6">
        <h2 className="text-xl font-semibold mb-2">Addresses</h2>
        {employee.addressList?.length > 0 ? (
          <ul>
            {employee.addressList.map((addr) => (
              <li key={addr.id} className="p-3 border border-gray-300 rounded-md mb-2">
                <p><strong>Address:</strong> {addr.addressLine}, {addr.city}, {addr.state}, {addr.zipCode}, {addr.country.name}</p>
                <p className="text-sm text-gray-500">{addr.isCurrent ? "Currently Residing Here" : "Previous Address"}</p>
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-gray-500">No addresses available.</p>
        )}
      </div>

      {/* Subordinates */}
      <div className="bg-gray-100 p-4 rounded-lg mb-6">
        <h2 className="text-xl font-semibold mb-2">Subordinates</h2>
        {employee.subordinates?.length > 0 ? (
          <ul>
            {employee.subordinates.map((sub) => (
              <SubordinateCard key={sub.id} employee={sub} />
            ))}
          </ul>
        ) : (
          <p className="text-gray-500">No subordinates.</p>
        )}
      </div>

      <Link to="/" className="block text-center bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700">
        Back to List
      </Link>
    </div>
  );
};

// Recursive component for displaying subordinates
const SubordinateCard = ({ employee }) => {
  const [showDetails, setShowDetails] = useState(false);

  return (
    <li className="p-4 border border-gray-300 rounded-lg mb-3 bg-white shadow-sm">
      <div className="flex justify-between items-center">
        <div>
          <p className="font-bold text-lg">{employee.name}</p>
          <p className="text-sm text-gray-600">{employee.email} - {employee.country.name}</p>
        </div>
        <button
          onClick={() => setShowDetails(!showDetails)}
          className="text-blue-600 hover:underline"
        >
          {showDetails ? "Hide Details" : "View Details"}
        </button>
      </div>

      {showDetails && (
        <div className="mt-3 pl-4 border-l-2 border-gray-300">
          <p><strong>Date of Birth:</strong> {new Date(employee.date_of_birth).toLocaleDateString()}</p>

          {/* Departments */}
          {employee.employeeDepartmentList?.length > 0 && (
            <div className="mt-2">
              <p className="font-semibold">Departments:</p>
              <ul className="list-disc pl-5">
                {employee.employeeDepartmentList.map((dept) => (
                  <li key={dept.id}>{dept.department.name}</li>
                ))}
              </ul>
            </div>
          )}

          {/* Addresses */}
          {employee.addressList?.length > 0 && (
            <div className="mt-2">
              <p className="font-semibold">Addresses:</p>
              {employee.addressList.map((addr) => (
                <p key={addr.id} className="text-sm">
                  {addr.addressLine}, {addr.city}, {addr.state}, {addr.zipCode} ({addr.isCurrent ? "Current" : "Previous"})
                </p>
              ))}
            </div>
          )}

          {/* Subordinates */}
          {employee.subordinates?.length > 0 && (
            <div className="mt-2">
              <p className="font-semibold">Subordinates:</p>
              <ul>
                {employee.subordinates.map((sub) => (
                  <SubordinateCard key={sub.id} employee={sub} />
                ))}
              </ul>
            </div>
          )}
        </div>
      )}
    </li>
  );
};

export default EmployeeDetail;
