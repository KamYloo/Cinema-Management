import { BASE_API_URL } from "../Redux/api.js";

const LOCAL_MEDIA_HOSTS = new Set(["localhost", "127.0.0.1", "100.100.119.110"]);

export function resolveMediaUrl(url) {
    if (!url) {
        return "";
    }

    if (url.startsWith("/api/files/")) {
        return `${BASE_API_URL}${url}`;
    }

    try {
        const parsedUrl = new URL(url);

        if (parsedUrl.pathname.startsWith("/api/files/") && LOCAL_MEDIA_HOSTS.has(parsedUrl.hostname)) {
            return `${BASE_API_URL}${parsedUrl.pathname}${parsedUrl.search}${parsedUrl.hash}`;
        }
    } catch {
        if (url.includes("/api/files/")) {
            const filePath = url.slice(url.indexOf("/api/files/"));
            return `${BASE_API_URL}${filePath}`;
        }
    }

    return url;
}