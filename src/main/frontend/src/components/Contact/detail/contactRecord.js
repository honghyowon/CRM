import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './contactRecord.css';

function ContactRecord() {
    const location = useLocation();
    const contactData = location.state?.contactData || {};
    const navigate = useNavigate();

    return (
        <div className="contact-details-container">
            <h2 className="contact-details-title">Contact Details</h2>
            <div className="contact-details-content">
                <div className="contact-details-item">
                    <span className="contact-details-label">Contact Name:</span>
                    <span className="contact-details-value">{contactData.name}</span>
                </div>
                <div className="contact-details-item">
                    <span className="contact-details-label">Account Name:</span>
                    <span className="contact-details-value">{contactData.account.name}</span>
                </div>
                <div className="contact-details-item">
                    <span className="contact-details-label">Phone:</span>
                    <span className="contact-details-value">{contactData.phone}</span>
                </div>
                <div className="contact-details-item">
                    <span className="contact-details-label">Description:</span>
                    <span className="contact-details-value">{contactData.description || 'N/A'}</span>
                </div>
                <div className="contact-details-item">
                    <span className="contact-details-label">Created Date:</span>
                    <span className="contact-details-value">{contactData.createdDate}</span>
                </div>
                <div className="contact-details-item">
                    <span className="contact-details-label">Owner:</span>
                    <span className="contact-details-value">{contactData.userId || 'N/A'}</span>
                    {/* <span className="contact-details-value">{contactData.contactOwner?.name || 'N/A'}</span> */}
                </div>
            </div>
            <button className="back-button" onClick={() => navigate(-1)}>Back to Contacts</button>
        </div>
    );
}

export default ContactRecord;