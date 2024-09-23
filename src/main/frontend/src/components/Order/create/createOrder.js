import React, { useState } from "react";
import { Link, useLocation, useNavigate } from 'react-router-dom';
import axios from "axios";
import './createOrder.css';

function CreateOrder() {
    const navigate = useNavigate();
    const location = useLocation();
    const orderData = location.state?.orderData || {};

    const [orderName, setOrderName] = useState(orderData.orderName || "");
    const [stage,           setStage]           = useState(orderData.stage || "");
    const [amount,          setAmount]          = useState(orderData.amount || "");
    const [orderDate,       setOrderDate]       = useState(orderData.orderDate || "");
    const [opportunityId,       setOpportunityId]       = useState(location.state === null ? "" : orderData.opportunity.opportunityId);
    const [message,         setMessage]         = useState("");

    const userId = localStorage.getItem('userId');

    const handleSubmit = async (event) => {
        event.preventDefault();

        if(location.state === null) {
            try {
                const response = await axios.post("http://localhost:8080/api/order/create", {
                    orderName:       orderName,
                    stage:      stage,
                    amount:     amount,
                    orderDate:  orderDate,
                    opportunityId:  opportunityId,
                    userId:     userId,
                });
    
                console.log(response);
                
                if (response.data.error == null) {
                    navigate('/order');
                } else {
                    setMessage("Insert order failed : " + JSON.stringify(response.data.error));
                }
            } catch (error) {
                console.error("There was an error during signup!", error);
                setMessage("An error occurred during signup. Please try again.");
            }
        } else {
            try {
                const response = await axios.put(`http://localhost:8080/api/order/update/${orderData.orderId}`, {
                    orderName:       orderName,
                    stage:      stage,
                    amount:     amount,
                    orderDate:  orderDate,
                    opportunityId:  opportunityId,
                    userId:     userId,
                });

                if (response.data.error == null) {
                    navigate('/order');
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
            <h2>New order</h2>
            <form onSubmit={handleSubmit} className="login-form">
                <div className="form-group">
                    <label htmlFor="orderName">Order Name:</label>
                    <input
                        type="text"
                        id="orderName"
                        value={orderName}
                        onChange={(e) => setOrderName(e.target.value)}
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
                    <label htmlFor="orderDate">Order Date:</label>
                    <input
                        type="date"
                        id="orderDate"
                        value={orderDate}
                        onChange={(e) => setOrderDate(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="opportunityId">Opportunity ID:</label>
                    <input
                        type="text"
                        id="opportunityId"
                        value={opportunityId}
                        onChange={(e) => setOpportunityId(e.target.value)}
                    />
                </div>
                <button type="submit" className="login-button">Save</button>
            </form>
            <Link to="/order">
                <button className="signup-button">Cancel</button>
            </Link>
            {message && <p className="message">{message}</p>}
        </div>
    );
}

export default CreateOrder;
