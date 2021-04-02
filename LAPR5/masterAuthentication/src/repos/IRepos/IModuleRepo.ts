import { Repo } from "../../core/infra/Repo";
import { Module } from "../../domain/module/Module";

export default interface IModuleRepo extends Repo<Module> {
  findAll (): Promise<Array<Module>>;
}