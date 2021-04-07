package ca.pocable.etherconnect;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.1.1.
 */
@SuppressWarnings(value = {"rawtypes"})
public class MinecraftWorld extends Contract {
    private static final String BINARY = "60806040523480156200001157600080fd5b5060405162001e1938038062001e19833981018060405260608110156200003757600080fd5b81019080805190602001909291908051906020019092919080516401000000008111156200006457600080fd5b828101905060208101848111156200007b57600080fd5b81518560018202830111640100000000821117156200009957600080fd5b5050929190505050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555082600460006101000a81548160ff02191690831515021790555081600460016101000a81548163ffffffff021916908363ffffffff1602179055508060059080519060200190620001349291906200013e565b50505050620001ed565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200018157805160ff1916838001178555620001b2565b82800160010185558215620001b2579182015b82811115620001b157825182559160200191906001019062000194565b5b509050620001c19190620001c5565b5090565b620001ea91905b80821115620001e6576000816000905550600101620001cc565b5090565b90565b611c1c80620001fd6000396000f3fe608060405260043610610115576000357c0100000000000000000000000000000000000000000000000000000000900480622515721461011a578062d8d15914610189578063016a3738146101ea5780630427d9bd1461023b578063172accfa146102985780633ad54ecb1461030c5780633d3b8a731461038d5780634a3d20b81461040f5780635a100f4b146104505780636bbf0d5f1461056857806387571410146105e95780638da5cb5b14610620578063981b1d3414610677578063ab084b1214610707578063bb39a5b1146107ff578063c1fb54e51461084e578063e285bd6414610908578063e90a83e714610937578063f2fde38b146109b8578063f3fef3a314610a09578063fce3bbb514610a64575b600080fd5b34801561012657600080fd5b506101876004803603608081101561013d57600080fd5b81019080803563ffffffff169060200190929190803563ffffffff169060200190929190803563ffffffff169060200190929190803561ffff169060200190929190505050610aa1565b005b34801561019557600080fd5b506101e8600480360360408110156101ac57600080fd5b81019080803563ffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610ac1565b005b3480156101f657600080fd5b506102396004803603602081101561020d57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610bd8565b005b34801561024757600080fd5b5061027a6004803603602081101561025e57600080fd5b81019080803563ffffffff169060200190929190505050610c4c565b604051808261ffff1661ffff16815260200191505060405180910390f35b6102ea600480360360608110156102ae57600080fd5b81019080803563ffffffff169060200190929190803563ffffffff169060200190929190803563ffffffff169060200190929190505050610c6d565b604051808263ffffffff1663ffffffff16815260200191505060405180910390f35b34801561031857600080fd5b5061038b6004803603608081101561032f57600080fd5b81019080803563ffffffff169060200190929190803563ffffffff169060200190929190803563ffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610cae565b005b6103ed600480360360808110156103a357600080fd5b81019080803563ffffffff169060200190929190803563ffffffff169060200190929190803563ffffffff169060200190929190803561ffff169060200190929190505050610cc8565b604051808263ffffffff1663ffffffff16815260200191505060405180910390f35b34801561041b57600080fd5b5061044e6004803603602081101561043257600080fd5b81019080803563ffffffff169060200190929190505050610d0b565b005b34801561045c57600080fd5b506105466004803603608081101561047357600080fd5b81019080803563ffffffff169060200190929190803563ffffffff169060200190929190803563ffffffff169060200190929190803590602001906401000000008111156104c057600080fd5b8201836020820111156104d257600080fd5b803590602001918460018302840111640100000000831117156104f457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610d8a565b604051808263ffffffff1663ffffffff16815260200191505060405180910390f35b34801561057457600080fd5b506105e76004803603608081101561058b57600080fd5b81019080803563ffffffff169060200190929190803563ffffffff169060200190929190803563ffffffff169060200190929190803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610daa565b005b3480156105f557600080fd5b506105fe610e25565b604051808263ffffffff1663ffffffff16815260200191505060405180910390f35b34801561062c57600080fd5b50610635610e3b565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561068357600080fd5b5061068c610e60565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156106cc5780820151818401526020810190506106b1565b50505050905090810190601f1680156106f95780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561071357600080fd5b506107dd6004803603604081101561072a57600080fd5b81019080803563ffffffff1690602001909291908035906020019064010000000081111561075757600080fd5b82018360208201111561076957600080fd5b8035906020019184600183028401116401000000008311171561078b57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610efe565b604051808263ffffffff1663ffffffff16815260200191505060405180910390f35b34801561080b57600080fd5b5061084c6004803603604081101561082257600080fd5b81019080803563ffffffff169060200190929190803561ffff1690602001909291905050506110a0565b005b34801561085a57600080fd5b5061088d6004803603602081101561087157600080fd5b81019080803563ffffffff16906020019092919050505061121c565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156108cd5780820151818401526020810190506108b2565b50505050905090810190601f1680156108fa5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561091457600080fd5b5061091d6112cc565b604051808215151515815260200191505060405180910390f35b34801561094357600080fd5b506109766004803603602081101561095a57600080fd5b81019080803563ffffffff1690602001909291905050506112df565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156109c457600080fd5b50610a07600480360360208110156109db57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611312565b005b348015610a1557600080fd5b50610a6260048036036040811015610a2c57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506113b0565b005b348015610a7057600080fd5b50610a9f60048036036020811015610a8757600080fd5b8101908080351515906020019092919050505061147c565b005b6000610aae8585856114f4565b9050610aba81836110a0565b5050505050565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1614151515610afd57600080fd5b3373ffffffffffffffffffffffffffffffffffffffff16600160008463ffffffff1663ffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610b7657600080fd5b80600160008463ffffffff1663ffffffff16815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610c3357600080fd5b8073ffffffffffffffffffffffffffffffffffffffff16ff5b60026020528060005260406000206000915054906101000a900461ffff1681565b6000600460009054906101000a900460ff161515610c8a57600080fd5b6000610c978585856114f4565b9050610ca3338261155c565b809150509392505050565b610cc2610cbc8585856114f4565b82610ac1565b50505050565b6000600460009054906101000a900460ff161515610ce557600080fd5b6000610cf28686866114f4565b9050610cff813385611770565b80915050949350505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610d6657600080fd5b80600460016101000a81548163ffffffff021916908363ffffffff16021790555050565b6000610da0610d9a8686866114f4565b83610efe565b9050949350505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141515610e0557600080fd5b6000610e128585856114f4565b9050610e1e828261155c565b5050505050565b600460019054906101000a900463ffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60058054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610ef65780601f10610ecb57610100808354040283529160200191610ef6565b820191906000526020600020905b815481529060010190602001808311610ed957829003601f168201915b505050505081565b6000600460009054906101000a900460ff161515610f1b57600080fd5b600073ffffffffffffffffffffffffffffffffffffffff16600160008563ffffffff1663ffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515610f9557600080fd5b603c825111151515610fa657600080fd5b610fb38333610fa16119b0565b81600360008563ffffffff1663ffffffff1681526020019081526020016000209080519060200190610fe6929190611b4b565b507fda757c00ee890b133b21e01cab3355625b8ea0117b66a01ba50d6432d092daf98383604051808363ffffffff1663ffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561105c578082015181840152602081019050611041565b50505050905090810190601f1680156110895780820380516001836020036101000a031916815260200191505b50935050505060405180910390a182905092915050565b3373ffffffffffffffffffffffffffffffffffffffff16600160008463ffffffff1663ffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614151561111957600080fd5b600460019054906101000a900463ffffffff1663ffffffff168161ffff161115151561114457600080fd5b60008161ffff1611151561115757600080fd5b80600260008463ffffffff1663ffffffff16815260200190815260200160002060006101000a81548161ffff021916908361ffff1602179055507f19ec1a232a9e81e21918ecaa90c085e565acf6f4f2a194f7f935bab42bed97cb338383604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018363ffffffff1663ffffffff1681526020018261ffff1661ffff168152602001935050505060405180910390a15050565b60036020528060005260406000206000915090508054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156112c45780601f10611299576101008083540402835291602001916112c4565b820191906000526020600020905b8154815290600101906020018083116112a757829003601f168201915b505050505081565b600460009054906101000a900460ff1681565b60016020528060005260406000206000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561136d57600080fd5b806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561140b57600080fd5b803073ffffffffffffffffffffffffffffffffffffffff16311015151561143157600080fd5b8173ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f19350505050158015611477573d6000803e3d6000fd5b505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156114d757600080fd5b80600460006101000a81548160ff02191690831515021790555050565b6000610fff8463ffffffff161115801561151557506101008363ffffffff16105b80156115295750610fff8263ffffffff1611155b151561153457600080fd5b81600c8463ffffffff169060020a0260148663ffffffff169060020a02010190509392505050565b600073ffffffffffffffffffffffffffffffffffffffff16600160008363ffffffff1663ffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415156115d657600080fd5b81600160008363ffffffff1663ffffffff16815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610fa0600260008363ffffffff1663ffffffff16815260200190815260200160002060006101000a81548161ffff021916908361ffff1602179055507f057a81f17eff4201a1f2cf3e67ed9f8d271e94855215eabe80058e7adbe901298282604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018263ffffffff1663ffffffff1681526020019250505060405180910390a17f19ec1a232a9e81e21918ecaa90c085e565acf6f4f2a194f7f935bab42bed97cb3382610fa0604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018363ffffffff1663ffffffff1681526020018261ffff168152602001935050505060405180910390a15050565b600073ffffffffffffffffffffffffffffffffffffffff16600160008563ffffffff1663ffffffff16815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415156117ea57600080fd5b600460019054906101000a900463ffffffff1663ffffffff168161ffff161115151561181557600080fd5b81600160008563ffffffff1663ffffffff16815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600260008563ffffffff1663ffffffff16815260200190815260200160002060006101000a81548161ffff021916908361ffff1602179055507f057a81f17eff4201a1f2cf3e67ed9f8d271e94855215eabe80058e7adbe901298284604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018263ffffffff1663ffffffff1681526020019250505060405180910390a17f19ec1a232a9e81e21918ecaa90c085e565acf6f4f2a194f7f935bab42bed97cb338483604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018363ffffffff1663ffffffff1681526020018261ffff1661ffff168152602001935050505060405180910390a1505050565b81600160008563ffffffff1663ffffffff16815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600260008563ffffffff1663ffffffff16815260200190815260200160002060006101000a81548161ffff021916908361ffff1602179055507f057a81f17eff4201a1f2cf3e67ed9f8d271e94855215eabe80058e7adbe901298284604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018263ffffffff1663ffffffff1681526020019250505060405180910390a17f19ec1a232a9e81e21918ecaa90c085e565acf6f4f2a194f7f935bab42bed97cb338483604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018363ffffffff1663ffffffff1681526020018261ffff1661ffff168152602001935050505060405180910390a1505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611b8c57805160ff1916838001178555611bba565b82800160010185558215611bba579182015b82811115611bb9578251825591602001919060010190611b9e565b5b509050611bc79190611bcb565b5090565b611bed91905b80821115611be9576000816000905550600101611bd1565b5090565b9056fea165627a7a7230582006efbfe57924dc60419f1bac02fe7da8558ebdf3282a6e9bb56146fcb365e8f70029";

    public static final String FUNC_SETBLOCKDATA = "setBlockData";

    public static final String FUNC_SENDBLOCK = "sendBlock";

    public static final String FUNC_DESTROYCONTRACT = "destroyContract";

    public static final String FUNC_BLOCKDATA = "blockData";

    public static final String FUNC_PURCHASEBLOCK = "purchaseBlock";

    public static final String FUNC_SETMAXBLOCKID = "setMaxBlockId";

    public static final String FUNC_PLACESIGN = "placeSign";

    public static final String FUNC_SETBLOCKOWNINGADDRESS = "setBlockOwningAddress";

    public static final String FUNC_MAXBLOCKID = "maxBlockId";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_WORLDSEED = "worldSeed";

    public static final String FUNC_BLOCKMETADATA = "blockMetaData";

    public static final String FUNC_CURRENTLYSELLING = "currentlySelling";

    public static final String FUNC_BLOCKOWNERS = "blockOwners";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_SETSELLING = "setSelling";

    public static final Event UPDATEBLOCK_EVENT = new Event("UpdateBlock", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint32>() {}, new TypeReference<Uint16>() {}));
    ;

    public static final Event UPDATEBLOCKOWNER_EVENT = new Event("UpdateBlockOwner", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint32>() {}));
    ;

    public static final Event UPDATEBLOCKMETA_EVENT = new Event("UpdateBlockMeta", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected MinecraftWorld(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MinecraftWorld(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MinecraftWorld(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MinecraftWorld(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> setBlockData(BigInteger x, BigInteger y, BigInteger z, BigInteger newBlockID) {
        final Function function = new Function(
                FUNC_SETBLOCKDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(x), 
                new org.web3j.abi.datatypes.generated.Uint32(y), 
                new org.web3j.abi.datatypes.generated.Uint32(z), 
                new org.web3j.abi.datatypes.generated.Uint16(newBlockID)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> sendBlock(BigInteger blockCoordID, String other) {
        final Function function = new Function(
                FUNC_SENDBLOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(blockCoordID), 
                new org.web3j.abi.datatypes.Address(other)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> destroyContract(String ownerAddress) {
        final Function function = new Function(
                FUNC_DESTROYCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(ownerAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> blockData(BigInteger param0) {
        final Function function = new Function(FUNC_BLOCKDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint16>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> purchaseBlock(BigInteger x, BigInteger y, BigInteger z, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_PURCHASEBLOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(x), 
                new org.web3j.abi.datatypes.generated.Uint32(y), 
                new org.web3j.abi.datatypes.generated.Uint32(z)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> sendBlock(BigInteger x, BigInteger y, BigInteger z, String other) {
        final Function function = new Function(
                FUNC_SENDBLOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(x), 
                new org.web3j.abi.datatypes.generated.Uint32(y), 
                new org.web3j.abi.datatypes.generated.Uint32(z), 
                new org.web3j.abi.datatypes.Address(other)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> purchaseBlock(BigInteger x, BigInteger y, BigInteger z, BigInteger blockID, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_PURCHASEBLOCK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(x), 
                new org.web3j.abi.datatypes.generated.Uint32(y), 
                new org.web3j.abi.datatypes.generated.Uint32(z), 
                new org.web3j.abi.datatypes.generated.Uint16(blockID)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> setMaxBlockId(BigInteger maxBlock) {
        final Function function = new Function(
                FUNC_SETMAXBLOCKID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(maxBlock)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> placeSign(BigInteger x, BigInteger y, BigInteger z, String text) {
        final Function function = new Function(
                FUNC_PLACESIGN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(x), 
                new org.web3j.abi.datatypes.generated.Uint32(y), 
                new org.web3j.abi.datatypes.generated.Uint32(z), 
                new org.web3j.abi.datatypes.Utf8String(text)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setBlockOwningAddress(BigInteger x, BigInteger y, BigInteger z, String newBlockOwner) {
        final Function function = new Function(
                FUNC_SETBLOCKOWNINGADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(x), 
                new org.web3j.abi.datatypes.generated.Uint32(y), 
                new org.web3j.abi.datatypes.generated.Uint32(z), 
                new org.web3j.abi.datatypes.Address(newBlockOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> maxBlockId() {
        final Function function = new Function(FUNC_MAXBLOCKID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> worldSeed() {
        final Function function = new Function(FUNC_WORLDSEED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> placeSign(BigInteger loc, String text) {
        final Function function = new Function(
                FUNC_PLACESIGN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(loc), 
                new org.web3j.abi.datatypes.Utf8String(text)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setBlockData(BigInteger loc, BigInteger newBlockID) {
        final Function function = new Function(
                FUNC_SETBLOCKDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(loc), 
                new org.web3j.abi.datatypes.generated.Uint16(newBlockID)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> blockMetaData(BigInteger param0) {
        final Function function = new Function(FUNC_BLOCKMETADATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> currentlySelling() {
        final Function function = new Function(FUNC_CURRENTLYSELLING, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> blockOwners(BigInteger param0) {
        final Function function = new Function(FUNC_BLOCKOWNERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdraw(String withdrawLocation, BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(withdrawLocation), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setSelling(Boolean isSelling) {
        final Function function = new Function(
                FUNC_SETSELLING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(isSelling)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<UpdateBlockEventResponse> getUpdateBlockEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATEBLOCK_EVENT, transactionReceipt);
        ArrayList<UpdateBlockEventResponse> responses = new ArrayList<UpdateBlockEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpdateBlockEventResponse typedResponse = new UpdateBlockEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.blockLocation = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.newBlockID = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpdateBlockEventResponse> updateBlockEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, UpdateBlockEventResponse>() {
            @Override
            public UpdateBlockEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(UPDATEBLOCK_EVENT, log);
                UpdateBlockEventResponse typedResponse = new UpdateBlockEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.blockLocation = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.newBlockID = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UpdateBlockEventResponse> updateBlockEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATEBLOCK_EVENT));
        return updateBlockEventFlowable(filter);
    }

    public List<UpdateBlockOwnerEventResponse> getUpdateBlockOwnerEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATEBLOCKOWNER_EVENT, transactionReceipt);
        ArrayList<UpdateBlockOwnerEventResponse> responses = new ArrayList<UpdateBlockOwnerEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpdateBlockOwnerEventResponse typedResponse = new UpdateBlockOwnerEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.blockInformation = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpdateBlockOwnerEventResponse> updateBlockOwnerEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, UpdateBlockOwnerEventResponse>() {
            @Override
            public UpdateBlockOwnerEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(UPDATEBLOCKOWNER_EVENT, log);
                UpdateBlockOwnerEventResponse typedResponse = new UpdateBlockOwnerEventResponse();
                typedResponse.log = log;
                typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.blockInformation = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UpdateBlockOwnerEventResponse> updateBlockOwnerEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATEBLOCKOWNER_EVENT));
        return updateBlockOwnerEventFlowable(filter);
    }

    public List<UpdateBlockMetaEventResponse> getUpdateBlockMetaEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UPDATEBLOCKMETA_EVENT, transactionReceipt);
        ArrayList<UpdateBlockMetaEventResponse> responses = new ArrayList<UpdateBlockMetaEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpdateBlockMetaEventResponse typedResponse = new UpdateBlockMetaEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.blockLocation = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.metadata = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpdateBlockMetaEventResponse> updateBlockMetaEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, UpdateBlockMetaEventResponse>() {
            @Override
            public UpdateBlockMetaEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(UPDATEBLOCKMETA_EVENT, log);
                UpdateBlockMetaEventResponse typedResponse = new UpdateBlockMetaEventResponse();
                typedResponse.log = log;
                typedResponse.blockLocation = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.metadata = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UpdateBlockMetaEventResponse> updateBlockMetaEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATEBLOCKMETA_EVENT));
        return updateBlockMetaEventFlowable(filter);
    }

    @Deprecated
    public static MinecraftWorld load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MinecraftWorld(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MinecraftWorld load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MinecraftWorld(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MinecraftWorld load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new MinecraftWorld(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MinecraftWorld load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MinecraftWorld(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<MinecraftWorld> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, Boolean selling, BigInteger maxBlock, String worldSeedIn) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(selling), 
                new org.web3j.abi.datatypes.generated.Uint32(maxBlock), 
                new org.web3j.abi.datatypes.Utf8String(worldSeedIn)));
        return deployRemoteCall(MinecraftWorld.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<MinecraftWorld> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, Boolean selling, BigInteger maxBlock, String worldSeedIn) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(selling), 
                new org.web3j.abi.datatypes.generated.Uint32(maxBlock), 
                new org.web3j.abi.datatypes.Utf8String(worldSeedIn)));
        return deployRemoteCall(MinecraftWorld.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<MinecraftWorld> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, Boolean selling, BigInteger maxBlock, String worldSeedIn) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(selling), 
                new org.web3j.abi.datatypes.generated.Uint32(maxBlock), 
                new org.web3j.abi.datatypes.Utf8String(worldSeedIn)));
        return deployRemoteCall(MinecraftWorld.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<MinecraftWorld> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, Boolean selling, BigInteger maxBlock, String worldSeedIn) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(selling), 
                new org.web3j.abi.datatypes.generated.Uint32(maxBlock), 
                new org.web3j.abi.datatypes.Utf8String(worldSeedIn)));
        return deployRemoteCall(MinecraftWorld.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class UpdateBlockEventResponse {
        public Log log;

        public String user;

        public BigInteger blockLocation;

        public BigInteger newBlockID;
    }

    public static class UpdateBlockOwnerEventResponse {
        public Log log;

        public String newOwner;

        public BigInteger blockInformation;
    }

    public static class UpdateBlockMetaEventResponse {
        public Log log;

        public BigInteger blockLocation;

        public String metadata;
    }
}
