import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './accountRecord.css';

function AccountRecord() {
    const location = useLocation();
    const accountData = location.state?.accountData || {};
    const navigate = useNavigate();

    return (
        <div className="account-details-container">
            <h2 className="account-details-title">Account Details</h2>
            <div className="account-details-content">
                <div className="account-details-item">
                    <span className="account-details-label">Name:</span>
                    <span className="account-details-value">{accountData.name}</span>
                </div>
                <div className="account-details-item">
                    <span className="account-details-label">Type:</span>
                    <span className="account-details-value">{accountData.type}</span>
                </div>
                <div className="account-details-item">
                    <span className="account-details-label">Industry:</span>
                    <span className="account-details-value">{accountData.industry}</span>
                </div>
                <div className="account-details-item">
                    <span className="account-details-label">Description:</span>
                    <span className="account-details-value">{accountData.description || 'N/A'}</span>
                </div>
                <div className="account-details-item">
                    <span className="account-details-label">Created Date:</span>
                    <span className="account-details-value">{accountData.createdDate}</span>
                </div>
                <div className="account-details-item">
                    <span className="account-details-label">Owner:</span>
                    <span className="account-details-value">{accountData.userId || 'N/A'}</span>
                    {/* <span className="account-details-value">{accountData.accountOwner?.name || 'N/A'}</span> */}
                </div>
            </div>
            <button className="back-button" onClick={() => navigate(-1)}>Back to Accounts</button>
        </div>
    );
}

export default AccountRecord;