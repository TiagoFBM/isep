import {expect} from 'chai'
import { Result } from "../../../../src/core/logic/Result";
import { Line } from "../../../../src/domain/line/Line";
import { LineColor } from '../../../../src/domain/line/LineColor';
import ILineDTO from '../../../../src/dto/ILineDTO';

describe('Line Test', () => {

  /*
  it('Can create Line with all parameters valid.', () => {
    let expected = Result.ok<Line>();

    const linePaths = [{
      orientation: "Return"
    }, {
      orientation: "Go"
    }]

    const lineDTO = {
      code: "code",
      description: "description",
      linePaths: linePaths,
      allowedVehicles: ["Veiculo1"],
      deniedVehicles: ["Veiculo2"],
      allowedDrivers: ["Condutor1"],
      deniedDrivers: ["Condutor2"],
      lineColor: "RGB(255,0,0)",
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });
  */

  it('Can\'t create Line with null parameters.', () => {
    let expected = Result.fail<Line>("LineDTO can\'t be null.");

    let result = Line.create(null);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Can\'t create Line with null code parameter.', () => {
    let expected = Result.fail<Line>("LineDTO Code is invalid.");

    const linePaths = [{
      orientation: "Return"
    }, {
      orientation: "Go"
    }]

    const lineDTO = {
      code: null,
      description: "description",
      linePaths: linePaths,
      allowedVehicles: ["Veiculo1"],
      deniedVehicles: ["Veiculo2"],
      allowedDrivers: ["Condutor1"],
      deniedDrivers: ["Condutor2"],
      lineColor: "red",
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Can\'t create Line with empty code parameter.', () => {
    let expected = Result.fail<Line>("LineDTO Code is invalid.");

    const linePaths = [{
      orientation: "Return"
    }, {
      orientation: "Go"
    }]

    const lineDTO = {
      code: "",
      description: "description",
      linePaths: linePaths,
      allowedVehicles: ["Veiculo1"],
      deniedVehicles: ["Veiculo2"],
      allowedDrivers: ["Condutor1"],
      deniedDrivers: ["Condutor2"],
      lineColor: "red",
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Can\'t create Line with null description parameter.', () => {
    let expected = Result.fail<Line>("LineDTO Description is invalid.");

    const linePaths = [{
      orientation: "Return"
    }, {
      orientation: "Go"
    }]

    const lineDTO = {
      code: "code",
      description: null,
      linePaths: linePaths,
      allowedVehicles: ["Veiculo1"],
      deniedVehicles: ["Veiculo2"],
      allowedDrivers: ["Condutor1"],
      deniedDrivers: ["Condutor2"],
      lineColor: "red",
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Can\'t create Line with empty description parameter.', () => {
    let expected = Result.fail<Line>("LineDTO Description is invalid.");

    const linePaths = [{
      orientation: "Return"
    }, {
      orientation: "Go"
    }]

    const lineDTO = {
      code: "code",
      description: "",
      linePaths: linePaths,
      allowedVehicles: ["Veiculo1"],
      deniedVehicles: ["Veiculo2"],
      allowedDrivers: ["Condutor1"],
      deniedDrivers: ["Condutor2"],
      lineColor: "red",
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Can\'t create Line with null linePath parameter.', () => {
    let expected = Result.fail<Line>("LineDTO linePaths is invalid.");

    const lineDTO = {
      code: "code",
      description: "description",
      linePaths: null,
      allowedVehicles: ["Veiculo1"],
      deniedVehicles: ["Veiculo2"],
      allowedDrivers: ["Condutor1"],
      deniedDrivers: ["Condutor2"],
      lineColor: "red",
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Can\'t create Line with null allowedVehicles parameter.', () => {
    let expected = Result.fail<Line>("LineDTO allowedVehicles is invalid.");

    const linePaths = [{
      orientation: "Return"
    }, {
      orientation: "Go"
    }]

    const lineDTO = {
      code: "code",
      description: "description",
      linePaths: linePaths,
      allowedVehicles: null,
      deniedVehicles: ["Veiculo2"],
      allowedDrivers: ["Condutor1"],
      deniedDrivers: ["Condutor2"],
      lineColor: "red",
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Can\'t create Line with null deniedVehicles parameter.', () => {
    let expected = Result.fail<Line>("LineDTO deniedVehicles is invalid.");

    const linePaths = [{
      orientation: "Return"
    }, {
      orientation: "Go"
    }]

    const lineDTO = {
      code: "code",
      description: "description",
      linePaths: linePaths,
      allowedVehicles: ["Veiculo1"],
      deniedVehicles: null,
      allowedDrivers: ["Condutor1"],
      deniedDrivers: ["Condutor2"],
      lineColor: "red",
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Can\'t create Line with null allowedDrivers parameter.', () => {
    let expected = Result.fail<Line>("LineDTO allowedDrivers is invalid.");

    const linePaths = [{
      orientation: "Return"
    }, {
      orientation: "Go"
    }]

    const lineDTO = {
      code: "code",
      description: "description",
      linePaths: linePaths,
      allowedVehicles: ["Veiculo1"],
      deniedVehicles: ["Veiculo2"],
      allowedDrivers: null,
      deniedDrivers: ["Condutor2"],
      lineColor: "red",
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Can\'t create Line with null deniedDrivers parameter.', () => {
    let expected = Result.fail<Line>("LineDTO deniedDrivers is invalid.");

    const linePaths = [{
      orientation: "Return"
    }, {
      orientation: "Go"
    }]

    const lineDTO = {
      code: "code",
      description: "description",
      linePaths: linePaths,
      allowedVehicles: ["Veiculo1"],
      deniedVehicles: ["Veiculo2"],
      allowedDrivers: ["Condutor1"],
      deniedDrivers: null,
      lineColor: "red",
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  it('Can\'t create Line with null lineColor parameter.', () => {
    let expected = Result.fail<Line>("LineDTO lineColor is invalid.");

    const linePaths = [{
      orientation: "Return"
    }, {
      orientation: "Go"
    }]

    const lineDTO = {
      code: "code",
      description: "description",
      linePaths: linePaths,
      allowedVehicles: ["Veiculo1"],
      deniedVehicles: ["Veiculo2"],
      allowedDrivers: ["Condutor1"],
      deniedDrivers: ["Condutor2"],
      lineColor: null,
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });

  /*
  it('Can\'t create Line with empty lineColor parameter.', () => {
    let expected = Result.fail<Line>("Color Value must not be empty.");

    const linePaths = [{
      orientation: "Return"
    }, {
      orientation: "Go"
    }]

    const lineDTO = {
      code: "code",
      description: "description",
      linePaths: linePaths,
      allowedVehicles: ["Veiculo1"],
      deniedVehicles: ["Veiculo2"],
      allowedDrivers: ["Condutor1"],
      deniedDrivers: ["Condutor2"],
      lineColor: "",
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });
  */

  /*
  it('Can create Line with invalid color.', () => {
    let expected = Result.fail<Line>("Color Value must be valid.");

    const linePaths = [{
      orientation: "Return"
    }, {
      orientation: "Go"
    }]

    const lineDTO = {
      code: "code",
      description: "description",
      linePaths: linePaths,
      allowedVehicles: ["Veiculo1"],
      deniedVehicles: ["Veiculo2"],
      allowedDrivers: ["Condutor1"],
      deniedDrivers: ["Condutor2"],
      lineColor: "red",
    }

    let result = Line.create(lineDTO as ILineDTO);

    expect(expected.isFailure).to.equal(result.isFailure);
    expect(expected.isSuccess).to.equal(result.isSuccess);
    expect(expected.error).to.equal(result.error);
  });
*/
});