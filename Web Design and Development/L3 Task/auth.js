const express = require('express');
const router = express.Router();
const jwt = require('jsonwebtoken');
const User = require('../models/User');
const nodemailer = require('nodemailer');

// Register user
router.post('/register', async (req,res) => {
    const { name, email, password } = req.body;
    try {
        const user = new User({ name, email, password });
        await user.save();
        // Send verification email logic
        res.json({ message: 'Registered successfully, verify email!' });
    } catch(err) {
        res.status(400).json({ error: err.message });
    }
});

// Login user
router.post('/login', async (req,res) => {
    const { email, password } = req.body;
    const user = await User.findOne({ email });
    if(!user) return res.status(400).json({ error: 'User not found' });
    const isMatch = await user.comparePassword(password);
    if(!isMatch) return res.status(400).json({ error: 'Invalid password' });
    const token = jwt.sign({ id: user._id, role: user.role }, process.env.JWT_SECRET, { expiresIn: '1d' });
    res.json({ token, user });
});

module.exports = router;
