import { expect } from 'chai';
import { Result } from "../../../../src/core/logic/Result";
import { Node } from "../../../../src/domain/node/Node";
import INodeDTO from '../../../../src/dto/INodeDTO';

describe('Node Test', () => {

  it('Can create Node with all parameters valid.', () => {
    let expected = Result.ok<Node>();

    const list = [{node: "Node:11", duration:1234}];
    const nodeDTO = {
        code: "Node:11",
        name: "Baltar",
        latitude: "41.1937898023744", 
        longitude: "-8.38716802227697",
        shortName: "BALTR",
        isDepot: false,
        isReliefPoint: false,
        crewTravelTimes: list
    };

    let result = Node.create(nodeDTO as INodeDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with null DTO.', () => {
    let expected = Result.fail<Node>("NodeDTO can\'t be null.");
    let result = Node.create(null);
    
    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with null code.', () => {
    let expected = Result.fail<Node>("nodeDTO Code is invalid.");
    let result = Node.create({code: null,name: "Baltar", latitude:"41.1937898023744", longitude: "-8.38716802227697", shortName: "BALTR", isDepot: false, isReliefPoint: false});
    
    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with null name.', () => {
    let expected = Result.fail<Node>("nodeDTO Name is invalid.");
    let result = Node.create({code: "Node:11",name: null, latitude:"41.1937898023744", longitude: "-8.38716802227697", shortName: "BALTR", isDepot: false, isReliefPoint: false});
    
    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with null latitude.', () => {
    let expected = Result.fail<Node>("nodeDTO Latitude is invalid.");
    let result = Node.create({code: "Node:11",name: "Baltar", latitude:null, longitude: "-8.38716802227697", shortName: "BALTR", isDepot: false, isReliefPoint: false});
    
    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with null Longitude.', () => {
    let expected = Result.fail<Node>("nodeDTO Longitude is invalid.");
    let result = Node.create({code: "Node:11",name: "Baltar", latitude:"41.1937898023744", longitude: null, shortName: "BALTR", isDepot: false, isReliefPoint: false});
    
    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with null Short Name.', () => {
    let expected = Result.fail<Node>("nodeDTO Short Name is invalid.");
    let result = Node.create({code: "Node:11",name: "Baltar", latitude:"41.1937898023744", longitude: "-8.38716802227697", shortName: null, isDepot: false, isReliefPoint: false});
    
    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with null isDepot.', () => {
    let expected = Result.fail<Node>("nodeDTO Depot is invalid.");
    let result = Node.create({code: "Node:11",name: "Baltar", latitude:"41.1937898023744", longitude: "-8.38716802227697", shortName: "BALTR", isDepot: null, isReliefPoint: false});
    
    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with null isReliefPoint.', () => {
    let expected = Result.fail<Node>("nodeDTO Relief Point  is invalid.");
    let result = Node.create({code: "Node:11",name: "Baltar", latitude:"41.1937898023744", longitude: "-8.38716802227697", shortName: "BALTR", isDepot: false, isReliefPoint: null});
    
    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with invalid code (Size).', () => {
    let result = Node.create({code:"No:11:No:12:No:13:No:14:No:15",name: "Baltar", latitude:"41.1937898023744", longitude: "-8.38716802227697", shortName: "BALTR", isDepot: false, isReliefPoint: false});
    let expected = Result.fail<Node>("code is above the max size limit.");

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with empty code.', () => {
    let expected = Result.fail<Node>("nodeDTO Code is invalid.");
    let result = Node.create({code:"",name: "Baltar", latitude:"41.1937898023744", longitude: "-8.38716802227697", shortName: "BALTR", isDepot: false, isReliefPoint: false});

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with empty name.', () => {
    let expected = Result.fail<Node>("nodeDTO Name is invalid.");
    let result = Node.create({code:"Node:11",name: "", latitude:"41.1937898023744", longitude: "-8.38716802227697", shortName: "BALTR", isDepot: false, isReliefPoint: false});

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with invalid name (Size).', () => {
    let randomName:string="KDQBJjulkt5pkn8jNzKw1y1hcW4OpdFIZ6iVmfzzH1HruQYfp6ZS4GccOVjje6NIPvJDhhYGPg4kjNnveVfkHp6ygkUbzpaR3Ke9ul1oZ4rAnxOoyYIut7kCcHd2WHT5tN3Y8dCLpOTcWV5naQc2xe8hv9jhDH8rrJmAIbHmRhrGMflqOnO3UGqY6zxJRG8VcW0bS3NtmsM72WFZTlB61bRWlrKMy59ovV65QUlu1oXs5nUwLFq88B4qWiQbquXkCKgo";

    let expected = Result.fail<Node>("name is above the max size limit.");
    let result = Node.create({code:"Node:11", name: randomName, latitude:"41.1937898023744", longitude: "-8.38716802227697", shortName: "BALTR", isDepot: false, isReliefPoint: false});

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with invalid latitude.', () => {
    let result = Node.create({code:"Node:11", name: "Baltar", latitude:"latitude", longitude: "-8.38716802227697", shortName: "BALTR", isDepot: false, isReliefPoint: false});
    let expected = Result.fail<Node>("latitude is not in a valid WGS84 format.");

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with invalid longitude.', () => {
    let result = Node.create({code:"Node:11", name: "Baltar", latitude:"41.1937898023744", longitude: "longitude", shortName: "BALTR", isDepot: false, isReliefPoint: false});
    let expected = Result.fail<Node>("longitude is not in a valid WGS84 format.");

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with invalid shortName.', () => {
    let result = Node.create({code:"Node:11",name: "Baltar", latitude:"41.1937898023744", longitude: "-8.38716802227697", shortName: "Baltar", isDepot: false, isReliefPoint: false});
    let expected = Result.fail<Node>("name is not valid as Short Name for Node.");

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with empty shortName.', () => {
    let expected = Result.fail<Node>("nodeDTO Short Name is invalid.");
    let result = Node.create({code:"Node:11",name: "Baltar", latitude:"41.1937898023744", longitude: "-8.38716802227697", shortName: "", isDepot: false, isReliefPoint: false});

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Cant create Node with invalid shortName (Size).', () => {
    let randomShortName:string="QWERTYUIOPASDFGHJKLÇZXCVBNMQWERTYUIOPASDFGHJKLÇZXCVBNM";

    let expected = Result.fail<Node>("name is not valid as Short Name for Node.");
    let result = Node.create({code:"Node:11", name: "Baltar", latitude:"41.1937898023744", longitude: "-8.38716802227697", shortName: randomShortName, isDepot: false, isReliefPoint: false});

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

});