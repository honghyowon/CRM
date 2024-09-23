import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './opportunityRecord.css';

function OpportunityRecord() {
    const location = useLocation();
    const opportunityData = location.state?.opportunityData || {};
    const navigate = useNavigate();

    return (
        <div className="opportunity-details-container">
            <h2 className="opportunity-details-title">Opportunity Details</h2>
            <div className="opportunity-details-content">
                <div className="opportunity-details-item">
                    <span className="opportunity-details-label">Opportunity Name:</span>
                    <span className="opportunity-details-value">{opportunityData.name}</span>
                </div>
                <div className="opportunity-details-item">
                    <span className="opportunity-details-label">Account:</span>
                    <span className="opportunity-details-value">{opportunityData.account.name}</span>
                </div>
                <div className="opportunity-details-item">
                    <span className="opportunity-details-label">Stage:</span>
                    <span className="opportunity-details-value">{opportunityData.stage}</span>
                </div>
                <div className="opportunity-details-item">
                    <span className="opportunity-details-label">Amount:</span>
                    <span className="opportunity-details-value">{opportunityData.amount}</span>
                </div>
                <div className="opportunity-details-item">
                    <span className="opportunity-details-label">CloseDate:</span>
                    <span className="opportunity-details-value">{opportunityData.closeDate}</span>
                </div>
                <div className="opportunity-details-item">
                    <span className="opportunity-details-label">Created Date:</span>
                    <span className="opportunity-details-value">{opportunityData.createdDate}</span>
                </div>
                <div className="opportunity-details-item">
                    <span className="opportunity-details-label">Owner:</span>
                    <span className="opportunity-details-value">{opportunityData.userId || 'N/A'}</span>
                </div>
            </div>
            <button className="back-button" onClick={() => navigate(-1)}>Back</button>
        </div>
    );
}

export default OpportunityRecord;