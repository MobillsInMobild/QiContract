package com.example.contactServer.Model;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
//import sample.ShareSecret;

public class KGC extends KGCInstance{
	private BigInteger lm;
	private params a;
	private BigInteger xPub;
	@Override
	public params Setup() {
		int rBit=13;
		int qBit=7;
		TypeACurveGenerator pg = new TypeACurveGenerator(rBit, qBit);
		Pairing pairing = PairingFactory.getPairing(pg.generate());
		
		Field<?> g1 = pairing.getG1();
		Field<?> g2 = pairing.getGT();
		Element P = g1.newRandomElement().getImmutable();
		lm = StringTools.BigRandom(g1.getOrder());
		Element PT = P.pow(lm).getImmutable();
		a = new params(g1, g2, pairing, P, PT);
		return a;
	}

	@Override
	public Element PartialPrivateKeyExtract(byte[] id) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		Element q = a.H1(id);
        Element PrivateKeyD = q.pow(this.lm);
		return PrivateKeyD;
	}

	@Override
	public String deploy(Web3j web3, String publicKey, String privateKey, BigInteger userCo) throws Exception {
		// TODO Auto-generated method stub
		ContractGasProvider contractGasProvider = new DefaultGasProvider();
		Credentials credentials = Credentials.create(privateKey);
		return ShareSecret.deploy(web3, credentials, contractGasProvider, userCo).send().getContractAddress();
	}

	@Override
	public String claim(BigInteger x, Element pi, byte[] userId, ShareSecret secret) {
		// TODO Auto-generated method stub
		xPub = new BigInteger("0");
		String hash=null;
		if(a.p.pow(x).equals(pi)) {
			try {
				Thread.currentThread().sleep(200);
				hash=secret.Claim(Boolean.TRUE).send().getTransactionHash();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
				xPub.add(x);
		}
		else {
			System.out.println("failed");
			secret.Claim(false);
		}
		return hash;
	}

	@Override
	public BigInteger getXpub() {
		// TODO Auto-generated method stub
		return xPub;
	}

}
