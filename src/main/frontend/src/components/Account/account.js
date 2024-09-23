import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Drawer, List, ListItem, ListItemIcon, ListItemText, Toolbar, Typography, Divider } from '@mui/material';
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import ContactMailIcon from '@mui/icons-material/ContactMail';
import BusinessCenterIcon from '@mui/icons-material/BusinessCenter';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import './account.css';
import axios from "axios";

const drawerWidth = 240;

function Account() {
    const [rows, setRows] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/account");
                console.log(response);

                setRows(response.data);
            } catch (err) {
                console.error("Error fetching data:", err);
            }
        };

        fetchData();
    }, []);

    const deleteAccount = async (accountId) => {
        try {
            await axios.delete(`http://localhost:8080/api/account/delete/${accountId}`);
            setRows(rows.filter(row => row.accountId !== accountId));
        } catch (error) {
            console.error('There was an error deleting the account!', error);
        }
    };

    return (
        <div style={{ display: 'flex' }}>
            <Drawer
                variant="permanent"
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
                }}
            >
                <Toolbar />
                <div>
                    <List>
                        <ListItem>
                            <Typography variant="h6" noWrap component="div" style={{ paddingLeft: 16 }}>
                                CRM
                            </Typography>
                        </ListItem>
                        <Divider />
                        <Link to="/home">
                            <ListItem button>
                                <ListItemIcon>
                                    <DashboardIcon />
                                </ListItemIcon>
                                <ListItemText primary="Home" />
                            </ListItem>
                        </Link>
                        <ListItem button>
                            <ListItemIcon>
                                <AccountCircleIcon />
                            </ListItemIcon>
                            <ListItemText primary="Account" />
                        </ListItem>
                        <Link to="/contact">
                            <ListItem button>
                                <ListItemIcon>
                                    <ContactMailIcon />
                                </ListItemIcon>
                                <ListItemText primary="Contact" />
                            </ListItem>
                        </Link>
                        <Link to="/opportunity">
                            <ListItem button>
                                <ListItemIcon>
                                    <BusinessCenterIcon />
                                </ListItemIcon>
                                <ListItemText primary="Opportunity" />
                            </ListItem>
                        </Link>
                        <Link to="/order">
                            <ListItem button>
                                <ListItemIcon>
                                    <ShoppingCartIcon />
                                </ListItemIcon>
                                <ListItemText primary="Order" />
                            </ListItem>
                        </Link>
                    </List>
                </div>
            </Drawer>
            <main style={{ flexGrow: 1, padding: '24px' }}>
            <Toolbar />
                <Typography paragraph>
                    <Box sx={{ padding: 2 }}>
                    
                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead>
                                    <Link to="/account/create">
                                        <button className="new-account-button">생성</button>
                                    </Link>
                                    <TableRow>
                                        <TableCell>Account Name</TableCell>
                                        <TableCell>Type</TableCell>
                                        <TableCell>Industry</TableCell>
                                        <TableCell>createdDate</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                {rows.length > 0 ? (
                                    rows.map((row) => (
                                        <TableRow key={row.accountId}>
                                            <TableCell>
                                                <Link
                                                    to={`/account/details/${row.accountId}`}
                                                    state={{ accountData: row }}
                                                >
                                                    {row.name}
                                                </Link>
                                            </TableCell>
                                            <TableCell>{row.type}</TableCell>
                                            <TableCell>{row.industry}</TableCell>
                                            <TableCell>{row.createdDate}</TableCell>
                                            <TableCell>
                                                <Link
                                                    to="/account/update"
                                                    state={{ accountData: row }}  // 여기에 state 속성을 설정
                                                >
                                                    <button className='update-account-button'>수정</button>
                                                </Link>
                                                <button className='delete-account-button' onClick={() => deleteAccount(row.accountId)}>삭제</button>
                                            </TableCell>
                                        </TableRow>
                                    ))
                                ) : (
                                    <TableRow>
                                        <TableCell colSpan={5}>No data available</TableCell>
                                    </TableRow>
                                )}
                            </TableBody>
                            </Table>
                        </TableContainer>
                    </Box>
                </Typography>
            </main>
        </div>
    );
}

export default Account;
