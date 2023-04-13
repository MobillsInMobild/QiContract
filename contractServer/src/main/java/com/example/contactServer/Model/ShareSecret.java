package com.example.contactServer.Model;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
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
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class ShareSecret extends Contract {
    private static final String BINARY = "0x60806040526000600155600060025561271060035534801561002057600080fd5b50604051602080610f1b833981018060405281019080805190602001909291905050508060008190555050610ec18061005a6000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680632ca1512214610072578063365b98b214610089578063590e1ae3146100f2578063e60b362514610109578063ed21248c14610150575b600080fd5b34801561007e57600080fd5b5061008761015a565b005b34801561009557600080fd5b506100b46004803603810190808035906020019092919050505061035b565b604051808681526020018515151515815260200184151515158152602001831515151581526020018281526020019550505050505060405180910390f35b3480156100fe57600080fd5b506101076103c7565b005b34801561011557600080fd5b506101366004803603810190808035151590602001909291905050506106ea565b604051808215151515815260200191505060405180910390f35b610158610a8a565b005b60005460015410151561016c57600080fd5b60001515600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160009054906101000a900460ff1615151415156101ce57600080fd5b6001600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160006101000a81548160ff021916908315150217905550600154600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000181905550600560a060405190810160405280600081526020016000151581526020016000151581526020016000151581526020016000815250908060018154018082558091505090600182039060005260206000209060030201600090919290919091506000820151816000015560208201518160010160006101000a81548160ff02191690831515021790555060408201518160010160016101000a81548160ff02191690831515021790555060608201518160010160026101000a81548160ff0219169083151502179055506080820151816002015550505060018060008282540192505081905550565b60058181548110151561036a57fe5b90600052602060002090600302016000915090508060000154908060010160009054906101000a900460ff16908060010160019054906101000a900460ff16908060010160029054906101000a900460ff16908060020154905085565b6000546002541415156103d957600080fd5b612710600354141515156103ec57600080fd5b600015156005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000015481548110151561044157fe5b906000526020600020906003020160010160029054906101000a900460ff16151514151561046e57600080fd5b600354600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600001541015610589573373ffffffffffffffffffffffffffffffffffffffff166108fc670de0b6b3a76400009081150290604051600060405180830381858888f19350505050158015610508573d6000803e3d6000fd5b5060016005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000015481548110151561055c57fe5b906000526020600020906003020160010160026101000a81548160ff0219169083151502179055506106e8565b600354600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000015411156106e7573373ffffffffffffffffffffffffffffffffffffffff166108fc670de0b6b3a76400006001600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000015401029081150290604051600060405180830381858888f1935050505015801561066a573d6000803e3d6000fd5b5060016005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600001548154811015156106be57fe5b906000526020600020906003020160010160026101000a81548160ff0219169083151502179055505b5b565b6000806000546002541415156106ff57600080fd5b61271060035414151561071157600080fd5b600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600001549050600081148061079957506001151560056001830381548110151561077657fe5b906000526020600020906003020160010160019054906101000a900460ff161515145b15156107a457600080fd5b600015156005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600001548154811015156107f957fe5b906000526020600020906003020160010160019054906101000a900460ff16151514151561082657600080fd5b6005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000015481548110151561087757fe5b906000526020600020906003020160020154421115801561089c575060011515831515145b156109bb5760016005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600001548154811015156108f457fe5b906000526020600020906003020160010160016101000a81548160ff0219169083151502179055503373ffffffffffffffffffffffffffffffffffffffff166108fc670de0b6b3a76400006001600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000015401029081150290604051600060405180830381858888f193505050501580156109b1573d6000803e3d6000fd5b5060019150610a84565b60006005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000154815481101515610a0e57fe5b906000526020600020906003020160010160016101000a81548160ff021916908315150217905550600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000154600381905550600091505b50919050565b600054600154141515610a9c57600080fd5b600054600254101515610aae57600080fd5b600015156005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000154815481101515610b0357fe5b906000526020600020906003020160010160009054906101000a900460ff161515141515610b3057600080fd5b346005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000154815481101515610b8257fe5b906000526020600020906003020160000160008282540192505081905550670de0b6b3a76400006001600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000015401026005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000154815481101515610c4157fe5b906000526020600020906003020160000154101515610e935760016005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000154815481101515610cad57fe5b906000526020600020906003020160010160006101000a81548160ff0219169083151502179055506001610708600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000154010242016005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000154815481101515610d7257fe5b9060005260206000209060030201600201819055503373ffffffffffffffffffffffffffffffffffffffff166108fc670de0b6b3a76400006001600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000015401026005600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000154815481101515610e4257fe5b906000526020600020906003020160000154039081150290604051600060405180830381858888f19350505050158015610e80573d6000803e3d6000fd5b5060016002600082825401925050819055505b5600a165627a7a72305820d9a7bcdf51da51a479db6b91ed56ad8858d925834a185e86e8a7bbacac2a69680029";

    public static final String FUNC_USERS = "users";

    public static final String FUNC_SIGN = "sign";

    public static final String FUNC_DEPOSIT = "Deposit";

    public static final String FUNC_CLAIM = "Claim";

    public static final String FUNC_REFUND = "refund";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected ShareSecret(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ShareSecret(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ShareSecret(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ShareSecret(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Tuple5<BigInteger, Boolean, Boolean, Boolean, BigInteger>> users(BigInteger param0) {
        final Function function = new Function(FUNC_USERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, Boolean, Boolean, Boolean, BigInteger>>(function,
                new Callable<Tuple5<BigInteger, Boolean, Boolean, Boolean, BigInteger>>() {
                    @Override
                    public Tuple5<BigInteger, Boolean, Boolean, Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, Boolean, Boolean, Boolean, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (Boolean) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> sign() {
        final Function function = new Function(
                FUNC_SIGN, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> Deposit(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> Claim(Boolean secret) {
        final Function function = new Function(
                FUNC_CLAIM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(secret)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> refund() {
        final Function function = new Function(
                FUNC_REFUND, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ShareSecret load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ShareSecret(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ShareSecret load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ShareSecret(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ShareSecret load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ShareSecret(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ShareSecret load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ShareSecret(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ShareSecret> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger _all) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_all)));
        return deployRemoteCall(ShareSecret.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ShareSecret> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger _all) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_all)));
        return deployRemoteCall(ShareSecret.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ShareSecret> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _all) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_all)));
        return deployRemoteCall(ShareSecret.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ShareSecret> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _all) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_all)));
        return deployRemoteCall(ShareSecret.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
