import React, { useState } from "react";
import { Link, useLocation, useNavigate } from 'react-router-dom';
import axios from "axios";
import './createOpportunity.css';

function CreateOpportunity() {
    const navigate = useNavigate();
    const location = useLocation();
    const opportunityData = location.state?.opportunityData || {};

    const [opportunityName, setOpportunityName] = useState(opportunityData.name || "");
    const [stage,           setStage]           = useState(opportunityData.stage || "");
    const [amount,          setAmount]          = useState(opportunityData.amount || "");
    const [closeDate,       setCloseDate]       = useState(opportunityData.closeDate || "");
    const [accountId,       setAccountId]       = useState(location.state === null ? "" : opportunityData.account.accountId);    
    const [message,         setMessage]         = useState("");

    const userId = localStorage.getItem('userId');

    const handleSubmit = async (event) => {
        event.preventDefault();

        if(location.state === null) {
            try {
                const response = await axios.post("http://localhost:8080/api/opportunity/create", {
                    name:       opportunityName,
                    stage:      stage,
                    amount:     amount,
                    closeDate:  closeDate,
                    accountId:  accountId,
                    userId:     userId,
                });
    
                console.log(response);
                
                if (response.data.error == null) {
                    navigate('/opportunity');
                } else {
                    setMessage("Insert opportunity failed : " + JSON.stringify(response.data.error));
                }
            } catch (error) {
                console.error("There was an error during signup!", error);
                setMessage("An error occurred during signup. Please try again.");
            }
        } else {
            try {
                const response = await axios.put(`http://localhost:8080/api/opportunity/update/${opportunityData.opportunityId}`, {
                    name:       opportunityName,
                    stage:      stage,
                    amount:     amount,
                    closeDate:  closeDate,
                    accountId:  accountId,
                    userId:     userId,
                });

                if (response.data.error == null) {
                    navigate('/opportunity');
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
            <h2>New Opportunity</h2>
            <form onSubmit={handleSubmit} className="login-form">
                <div className="form-group">
                    <label htmlFor="opportunityName">Opportunity Name:</label>
                    <input
                        type="text"
                        id="opportunityName"
                        value={opportunityName}
                        onChange={(e) => setOpportunityName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="stage">Stage:</label>
                    <input
                        type="text"
                        id="stage"
                        value={stage}
                        onChange={(e) => setStage(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="amount">Amount:</label>
                    <input
                        type="number"
                        id="amount"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="closeDate">CloseDate:</label>
                    <input
                        type="date"
                        id="closeDate"
                        value={closeDate}
                        onChange={(e) => setCloseDate(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="phone">Account ID:</label>
                    <input
                        type="text"
                        id="accountId"
                        value={accountId}
                        onChange={(e) => setAccountId(e.target.value)}
                    />
                </div>
                <button type="submit" className="login-button">Save</button>
            </form>
            <Link to="/opportunity">
                <button className="signup-button">Cancel</button>
            </Link>
            {message && <p className="message">{message}</p>}
        </div>
    );
}

export default CreateOpportunity;
