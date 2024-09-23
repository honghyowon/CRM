import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Drawer, List, ListItem, ListItemIcon, ListItemText, Toolbar, Typography, Divider } from '@mui/material';
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';
import DashboardIcon from '@mui/icons-material/Dashboard';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import ContactMailIcon from '@mui/icons-material/ContactMail';
import BusinessCenterIcon from '@mui/icons-material/BusinessCenter';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import axios from 'axios';
import dayjs from 'dayjs'; // 날짜 처리를 위한 라이브러리

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const drawerWidth = 240;

const options = {
    responsive: true,
    plugins: {
        legend: {
            position: 'top',
        },
        title: {
            display: true,
            text: 'Monthly Opportunity & Order Amounts',
        },
    },
};

function Home() {
    const [rows, setRows] = useState([]);
    const [orderRows, setOrderRows] = useState([]);

    const [chartData, setChartData] = useState({
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        datasets: [
            {
                label: 'Opportunity Amount by Month',
                data: Array(12).fill(0), // 초기 값은 0으로 설정
                backgroundColor: '#42a5f5',
            },
            {
                label: 'Order Amount by Month',
                data: Array(12).fill(0), // 초기 값은 0으로 설정
                backgroundColor: '#1565c0',
            },
        ],
    });

    useEffect(() => {
        const fetchData = async () => {
            try {
                const opptyResponse = await axios.get('http://localhost:8080/api/opportunity');
                const opportunities = opptyResponse.data;

                const orderResponse = await axios.get('http://localhost:8080/api/order');
                const orders = orderResponse.data;

                // 데이터를 월별로 그룹화하고 합산
                const monthlyAmounts = Array(12).fill(0);
                opportunities.forEach((opportunity) => {
                    const month = dayjs(opportunity.closeDate).month(); // 월 추출 (0이 1월)
                    monthlyAmounts[month] += opportunity.amount; // 월별 금액 합산
                });

                const orderMonthlyAmounts = Array(12).fill(0);
                orders.forEach((order) => {
                    const month = dayjs(order.orderDate).month(); // 월 추출 (0이 1월)
                    orderMonthlyAmounts[month] += order.amount; // 월별 금액 합산
                });

                // 차트 데이터 업데이트
                setChartData((prevData) => ({
                    ...prevData,
                    datasets: [
                        {
                            ...prevData.datasets[0],
                            data: monthlyAmounts, // 새로 계산한 월별 amount
                        },
                        {
                            ...prevData.datasets[1],
                            data: orderMonthlyAmounts, // 새로 계산한 월별 amount
                            backgroundColor: '#1565c0',
                        },
                    ],
                }));

                setRows(opportunities);
                setOrderRows(orders);
            } catch (err) {
                console.error('Error fetching data:', err);
            }
        };

        fetchData();
    }, []);

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
                        <ListItem button>
                            <ListItemIcon>
                                <DashboardIcon />
                            </ListItemIcon>
                            <ListItemText primary="Home" />
                        </ListItem>
                        <Link to="/account">
                            <ListItem button>
                                <ListItemIcon>
                                    <AccountCircleIcon />
                                </ListItemIcon>
                                <ListItemText primary="Account" />
                            </ListItem>
                        </Link>
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
                        <Box sx={{ marginBottom: 4 }}>
                            <Bar options={options} data={chartData} />
                        </Box>
                    
                        <h1>Opportunity</h1>
                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Opportunity Name</TableCell>
                                        <TableCell>Account</TableCell>
                                        <TableCell>Stage</TableCell>
                                        <TableCell>Amount</TableCell>
                                        <TableCell>Close Date</TableCell>
                                        <TableCell>Created Date</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {rows.length > 0 ? (
                                        rows.map((row) => (
                                            <TableRow key={row.opportunityId}>
                                                <Link
                                                    to={`/opportunity/details/${row.opportunityId}`}
                                                    state={{ opportunityData: row }}
                                                >
                                                    <TableCell>{row.name}</TableCell>
                                                </Link>
                                                <TableCell>{row.account.name}</TableCell>
                                                <TableCell>{row.stage}</TableCell>
                                                <TableCell>{row.amount}</TableCell>
                                                <TableCell>{row.closeDate}</TableCell>
                                                <TableCell>{row.createdDate}</TableCell>
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
                        <br /><br /><br />
                        <h1>Order</h1>
                        <TableContainer component={Paper}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Order Name</TableCell>
                                        <TableCell>Opportunity</TableCell>
                                        <TableCell>Account</TableCell>
                                        <TableCell>Stage</TableCell>
                                        <TableCell>Amount</TableCell>
                                        <TableCell>Order Date</TableCell>
                                        <TableCell>Created Date</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {orderRows.length > 0 ? (
                                        orderRows.map((row) => (
                                            <TableRow key={row.orderId}>
                                                <Link
                                                    to={`/order/details/${row.orderId}`}
                                                    state={{ orderData: row }}
                                                >
                                                    <TableCell>{row.orderName}</TableCell>
                                                </Link>
                                                <TableCell>{row.opportunity.name}</TableCell>
                                                <TableCell>{row.account.name}</TableCell>
                                                <TableCell>{row.stage}</TableCell>
                                                <TableCell>{row.amount}</TableCell>
                                                <TableCell>{row.orderDate}</TableCell>
                                                <TableCell>{row.createdDate}</TableCell>
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

export default Home;
