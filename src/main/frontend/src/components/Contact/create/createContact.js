import React, { useState } from "react";
import { Link, useLocation, useNavigate } from 'react-router-dom';
import axios from "axios";
import './createContact.css';

function CreateContact() {
    const navigate = useNavigate();
    const location = useLocation();
    const contactData = location.state?.contactData || {};

    const [contactName, setContactName] = useState(contactData.name || "");
    const [phone,       setPhone]       = useState(contactData.phone || "");
    const [description, setDescription] = useState(contactData.description || "");
    const [accountId,   setAccountId]   = useState(contactData.accountId || "");
    const [message,     setMessage]     = useState("");

    const userId = localStorage.getItem('userId');

    const handleSubmit = async (event) => {
        event.preventDefault();

        if(location.state === null) {
            try {
                const response = await axios.post("http://localhost:8080/api/contact/create", {
                    name: contactName,
                    phone: phone,
                    description: description,
                    accountId: accountId,
                    userId: userId,
                });
    
                console.log(response);
                
                if (response.data.error == null) {
                    navigate('/contact');
                } else {
                    setMessage("Insert contact failed : " + JSON.stringify(response.data.error));
                }
            } catch (error) {
                console.error("There was an error during signup!", error);
                setMessage("An error occurred during signup. Please try again.");
            }
        } else {
            try {
                const response = await axios.put(`http://localhost:8080/api/contact/update/${contactData.contactId}`, {
                    name: contactName,
                    phone: phone,
                    description: description,
                    accountId: accountId,
                    userId: userId,
                });

                if (response.data.error == null) {
                    navigate('/contact');
                } else {
                    setMessage("Update failed: " + JSON.stringify(response.data.error));
                }
            } catch (error) {
                console.error("There was an error during update!", error);
                setMessage("An error occurred during update. Please try again.");
            }
        }
    };

    return (
        <div className="login-container">
            <h2>New Contact</h2>
            <form onSubmit={handleSubmit} className="login-form">
                <div className="form-group">
                    <label htmlFor="contactName">Contact Name:</label>
                    <input
                        type="text"
                        id="contactName"
                        value={contactName}
                        onChange={(e) => setContactName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="phone">Phone:</label>
                    <input
                        type="text"
                        id="phone"
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="description">Description:</label>
                    <textarea
                        id="description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="phone">Accoung ID:</label>
                    <input
                        type="text"
                        id="accountId"
                        value={accountId}
                        onChange={(e) => setAccountId(e.target.value)}
                    />
                </div>
                <button type="submit" className="login-button">Save</button>
            </form>
            <Link to="/contact">
                <button className="signup-button">Cancel</button>
            </Link>
            {message && <p className="message">{message}</p>}
        </div>
    );
}

export default CreateContact;
