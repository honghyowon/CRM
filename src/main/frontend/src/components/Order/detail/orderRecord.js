import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './orderRecord.css';

function OrderRecord() {
    const location = useLocation();
    const orderData = location.state?.orderData || {};
    const navigate = useNavigate();

    return (
        <div className="order-details-container">
            <h2 className="order-details-title">Order Details</h2>
            <div className="order-details-content">
                <div className="order-details-item">
                    <span className="order-details-label">Order Name:</span>
                    <span className="order-details-value">{orderData.orderName}</span>
                </div>
                <div className="order-details-item">
                    <span className="order-details-label">Opportunity:</span>
                    <span className="order-details-value">{orderData.opportunity.name}</span>
                </div>
                <div className="order-details-item">
                    <span className="order-details-label">Account:</span>
                    <span className="order-details-value">{orderData.account.name}</span>
                </div>
                <div className="order-details-item">
                    <span className="order-details-label">Stage:</span>
                    <span className="order-details-value">{orderData.stage}</span>
                </div>
                <div className="order-details-item">
                    <span className="order-details-label">Amount:</span>
                    <span className="order-details-value">{orderData.amount}</span>
                </div>
                <div className="order-details-item">
                    <span className="order-details-label">Order Date:</span>
                    <span className="order-details-value">{orderData.orderDate}</span>
                </div>
                <div className="order-details-item">
                    <span className="order-details-label">Created Date:</span>
                    <span className="order-details-value">{orderData.createdDate}</span>
                </div>
                <div className="order-details-item">
                    <span className="order-details-label">Owner:</span>
                    <span className="order-details-value">{orderData.userId || 'N/A'}</span>
                </div>
            </div>
            <button className="back-button" onClick={() => navigate(-1)}>Back</button>
        </div>
    );
}

export default OrderRecord;