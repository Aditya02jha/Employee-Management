import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import React from "react";

export default function Homepage() {
  const navigate = useNavigate();

  const images = [
    "/workplace1.jpg",
    "/workplace2.jpg",
    "/workplace3.jpg",
    "/workplace4.jpg",
    "/workplace5.jpg",
  ];

  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
    }, 5000); // Smooth transition every 5 seconds
    return () => clearInterval(interval);
  }, [images.length]);

  return (
    <div className="min-h-screen flex flex-col bg-black text-white">
      {/* Navbar */}
      <nav className="flex justify-between items-center px-6 py-4 bg-gray-900 shadow-md">
        <div className="text-2xl font-bold tracking-widest text-white">LOGO</div>
        <div className="flex gap-4">
          <button
            className="px-5 py-2 bg-gray-700 hover:bg-gray-600 transition rounded-lg text-white"
            onClick={() => navigate("/employees")}
          >
            Add Address
          </button>

          <button
            className="px-5 py-2 bg-gray-700 hover:bg-gray-600 transition rounded-lg text-white"
            onClick={() => navigate("/create")}
          >
            Add Employee
          </button>

          <button
            className="px-5 py-2 bg-gray-700 hover:bg-gray-600 transition rounded-lg text-white"
            onClick={() => navigate("/employees")}
          >
            List of All Employees
          </button>

          <img
            src="/profile.jpg"
            alt="Profile"
            className="w-10 h-10 rounded-full border-2 border-gray-500 hover:border-white transition"
          />
        </div>
      </nav>

      {/* Hero Section (Carousel) */}
      <div className="relative w-full h-[500px] overflow-hidden">
        <img
          src={images[currentIndex]}
          alt={`Workplace ${currentIndex + 1}`}
          className="w-full h-full object-cover transition-all duration-1000 opacity-90 hover:opacity-100"
        />
        {/* Navigation Buttons */}
        <button
          onClick={() =>
            setCurrentIndex((prev) => (prev - 1 + images.length) % images.length)
          }
          className="absolute top-1/2 left-4 bg-gray-800 text-white p-3 rounded-full shadow-md hover:bg-gray-600 transition"
        >
          ◀
        </button>
        <button
          onClick={() => setCurrentIndex((prev) => (prev + 1) % images.length)}
          className="absolute top-1/2 right-4 bg-gray-800 text-white p-3 rounded-full shadow-md hover:bg-gray-600 transition"
        >
          ▶
        </button>
      </div>

      {/* Footer */}
      <footer className="bg-gray-900 p-10 mt-auto">
        <div className="max-w-3xl mx-auto text-center">
          <h2 className="text-2xl font-semibold mb-4">Contact Us</h2>
          <div className="flex flex-col gap-4 bg-gray-800 p-6 rounded-lg shadow-lg">
            <input
              className="border border-gray-600 p-3 rounded-lg bg-gray-900 text-white placeholder-gray-400"
              type="text"
              placeholder="Your Name"
            />
            <input
              className="border border-gray-600 p-3 rounded-lg bg-gray-900 text-white placeholder-gray-400"
              type="email"
              placeholder="Your Email"
            />
            <textarea
              className="border border-gray-600 p-3 rounded-lg bg-gray-900 text-white placeholder-gray-400"
              placeholder="Your Message"
              rows="3"
            />
            <button className="px-6 py-3 bg-gray-700 hover:bg-gray-600 transition rounded-lg text-white font-semibold">
              Submit
            </button>
          </div>
          <p className="mt-6 text-gray-400 text-sm">
            &copy; 2025 Your Company. All rights reserved.
          </p>
        </div>
      </footer>
    </div>
  );
}
