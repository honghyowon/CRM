import React, { useState } from "react";
import { Link } from 'react-router-dom';
import axios from "axios";
import './signup.css';

function Signup() {
    const [loginId, setLoginId] = useState("");
    const [password, setPassword] = useState("");
    const [username, setUserName] = useState("");
    const [birthDate, setBirthDate] = useState("");
    const [email, setEmail] = useState("");
    const [message, setMessage] = useState("");

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const response = await axios.post("http://localhost:8080/api/user/signup", {
                loginId: loginId,
                password: password,
                name: username,
                birthDate: birthDate,
                email: email,
            });

            console.log(response);
            
            if (response.data.error == null) {
                setMessage("Signup successful! Please log in.");
            } else {
                setMessage("Signup failed : " + JSON.stringify(response.data.error));
            }
        } catch (error) {
            console.error("There was an error during signup!", error);
            setMessage("An error occurred during signup. Please try again.");
        }
    };

    return (
        <div className="login-container">
            <h2>Sign Up</h2>
            <form onSubmit={handleSubmit} className="login-form">
                <div className="form-group">
                    <label htmlFor="loginId">Login Id:</label>
                    <input
                        type="text"
                        id="loginId"
                        value={loginId}
                        onChange={(e) => setLoginId(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="userName">User Name:</label>
                    <input
                        type="text"
                        id="userName"
                        value={username}
                        onChange={(e) => setUserName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="birthDate">BirthDate:</label>
                    <input
                        type="Date"
                        id="birthDate"
                        value={birthDate}
                        onChange={(e) => setBirthDate(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="login-button">회원가입</button>
            </form>
            <Link to="/login">
                <button className="signup-button">로그인으로 돌아가기</button>
            </Link>
            {message && <p className="message">{message}</p>}
        </div>
    );
}

export default Signup;
