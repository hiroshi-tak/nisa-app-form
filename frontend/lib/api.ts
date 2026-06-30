"use client";

export async function apiFetch(url: string, options: RequestInit = {}) {

    let res = await fetch(url, {
        ...options,
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
            ...(options.headers || {}),
        },
    });

    // AccessToken期限切れ
    if (res.status === 401 || res.status === 403) {

        const refreshRes = await fetch("http://localhost:8080/api/auth/refresh", {
            method: "POST",
            credentials: "include",
        });

        if (refreshRes.ok) {
            // もう一回リトライ
            res = await fetch(url, {
                ...options,
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                    ...(options.headers || {}),
                },
            });
        } else {
            // refresh失敗 → ログアウト扱い
            window.location.href = "/auth/login";
        }
    }

    return res;
}

