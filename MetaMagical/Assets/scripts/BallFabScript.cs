using UnityEngine;
using System.Collections;
using MagicEngine;

public class BallFabScript : MonoBehaviour, MagicEntity
{

	Rigidbody rb;

	// Use this for initialization
	void Start ()
	{
		rb = GetComponent<Rigidbody> ();
	}
	
	// Update is called once per frame
	void Update ()
	{
	
	}

	public Transform getTransform() {
		return this.transform;
	}

	public Rigidbody getRigidbody() {
		return rb;
	}
}

