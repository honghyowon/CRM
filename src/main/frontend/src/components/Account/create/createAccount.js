import React, { useState } from "react";
import { Link, useLocation, useNavigate } from 'react-router-dom';
import axios from "axios";
import './createAccount.css';

function CreateAccount() {
    const navigate = useNavigate();
    const location = useLocation();
    const accountData = location.state?.accountData || {};

    const [accountName, setAccountName] = useState(accountData.name || "");
    const [phone,       setPhone]       = useState(accountData.phone || "");
    const [fax,         setFax]         = useState(accountData.fax || "");
    const [website,     setWebsite]     = useState(accountData.website || "");
    const [type,        setType]        = useState(accountData.type || "");
    const [industry,    setIndustry]    = useState(accountData.industry || "");
    const [description, setDescription] = useState(accountData.description || "");
    const [message,     setMessage]     = useState("");

    const userId = localStorage.getItem('userId');

    const handleSubmit = async (event) => {
        event.preventDefault();

        if(location.state === null) {
            try {
                const response = await axios.post("http://localhost:8080/api/account/create", {
                    name: accountName,
                    phone: phone,
                    fax: fax,
                    website: website,
                    type: type,
                    industry: industry,
                    description: description,
                    userId: userId
                });
    
                console.log(response);
                
                if (response.data.error == null) {
                    navigate('/account');
                } else {
                    setMessage("Insert Account failed : " + JSON.stringify(response.data.error));
                }
            } catch (error) {
                console.error("There was an error during signup!", error);
                setMessage("An error occurred during signup. Please try again.");
            }
        } else {
            try {
                const response = await axios.put(`http://localhost:8080/api/account/update/${accountData.accountId}`, {
                    name: accountName,
                    phone: phone,
                    fax: fax,
                    website: website,
                    type: type,
                    industry: industry,
                    description: description,
                    userId: userId
                });

                if (response.data.error == null) {
                    navigate('/account');
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
            <h2>New Account</h2>
            <form onSubmit={handleSubmit} className="login-form">
                <div className="form-group">
                    <label htmlFor="accountName">Account Name:</label>
                    <input
                        type="text"
                        id="accountName"
                        value={accountName}
                        onChange={(e) => setAccountName(e.target.value)}
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
                    <label htmlFor="fax">Fax:</label>
                    <input
                        type="text"
                        id="fax"
                        value={fax}
                        onChange={(e) => setFax(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="website">Website:</label>
                    <input
                        type="text"
                        id="website"
                        value={website}
                        onChange={(e) => setWebsite(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="type">Type:</label>
                    <input
                        type="text"
                        id="type"
                        value={type}
                        onChange={(e) => setType(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="industry">Industry:</label>
                    <input
                        type="text"
                        id="industry"
                        value={industry}
                        onChange={(e) => setIndustry(e.target.value)}
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
                <button type="submit" className="login-button">Save</button>
            </form>
            <Link to="/account">
                <button className="signup-button">Cancel</button>
            </Link>
            {message && <p className="message">{message}</p>}
        </div>
    );
}

export default CreateAccount;
