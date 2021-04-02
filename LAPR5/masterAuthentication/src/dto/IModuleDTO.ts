export default interface IModuleDTO {
    name: string;
    urlPath: string;
    viewPath: string;
    isExact: boolean;
    isNavItem: boolean;
    needsAdmin: boolean;
}