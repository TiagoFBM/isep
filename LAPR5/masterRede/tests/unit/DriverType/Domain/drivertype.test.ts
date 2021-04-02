import { expect } from 'chai';
import { Result } from "../../../../src/core/logic/Result";
import { DriverType } from "../../../../src/domain/driver/DriverType";

describe('Driver Type Test', () => {

  it('Can create DriverType with code and description.', () => {
    let expected = Result.ok<DriverType>();
    let result = DriverType.create({code:"Condutor1",description:"description1"});
    
    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create DriverType with null DTO.', () => {
    let expected = Result.fail<DriverType>("DriverTypeDTO can\'t be null.");
    let result = DriverType.create(null);
    
    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create DriverType with null description.', () => {
    let expected = Result.fail<DriverType>("DriverTypeDTO Description is invalid.");
    let result = DriverType.create({code: "code",description:null});
    
    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create DriverType with null code.', () => {
    let expected = Result.fail<DriverType>("DriverTypeDTO Code is invalid.");
    let result = DriverType.create({code: null,description:"description"});
    
    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create DriverType with invalid code (Size).', () => {
    let result = DriverType.create({code:"CondutorAntonioJoseMaria1",description:"description1"});
    let expected = Result.fail<DriverType>("code is above the max size limit.");

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create DriverType with empty code.', () => {
    let expected = Result.fail<DriverType>("DriverTypeDTO Code is invalid.");
    let result = DriverType.create({code:"",description:"description1"});

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create DriverType with empty description.', () => {
    let expected = Result.fail<DriverType>("DriverTypeDTO Description is invalid.");
    let result = DriverType.create({code:"Condutor1",description:""});

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create DriverType with invalid description (Size).', () => {
    let randomDesc:string="KDQBJjulkt5pkn8jNzKw1y1hcW4OpdFIZ6iVmfzzH1HruQYfp6ZS4GccOVjje6NIPvJDhhYGPg4kjNnveVfkHp6ygkUbzpaR3Ke9ul1oZ4rAnxOoyYIut7kCcHd2WHT5tN3Y8dCLpOTcWV5naQc2xe8hv9jhDH8rrJmAIbHmRhrGMflqOnO3UGqY6zxJRG8VcW0bS3NtmsM72WFZTlB61bRWlrKMy59ovV65QUlu1oXs5nUwLFq88B4qWiQbquXkCKgo";

    let expected = Result.fail<DriverType>("description is above the max size limit.");
    let result = DriverType.create({code:"Condutor1",description:randomDesc});

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

});