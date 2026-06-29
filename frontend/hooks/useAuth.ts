"use client";

import { useEffect, useState } from "react";

export function useAuth() {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const checkAuth = async () => {
        const res = await fetch("http://localhost:8080/api/user", {
            credentials: "include",
        });

        setIsLoggedIn(res.ok);
    };

    useEffect(() => {
        checkAuth();
    }, []);

    const login = async (username: string, password: string) => {
        await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify({ username, password }),
        });

        await checkAuth();
    };

    const logout = async () => {
        await fetch("http://localhost:8080/api/auth/logout", {
            method: "POST",
            credentials: "include",
        });

        setIsLoggedIn(false);
    };

    return { isLoggedIn, login, logout };
}

/*
export function useAuth() {
    const [token, setToken] = useState<string | null>(null);

    useEffect(() => {
        setToken(localStorage.getItem("token"));
    }, []);

    const login = (newToken: string) => {
        localStorage.setItem("token", newToken);
        setToken(newToken);
    };

    const logout = () => {
        localStorage.removeItem("token");
        setToken(null);
    };

    return {
        token,
        login,
        logout,
        isLoggedIn: !!token,
    };
}
*/