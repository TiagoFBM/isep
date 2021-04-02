import config from "../config";

export async function getAllModules() {
    let data = [];
    const res = await fetch(config.authApiURL + "/module");
    if (res.ok) {
        data = await res.json();
    }
    return data;
}