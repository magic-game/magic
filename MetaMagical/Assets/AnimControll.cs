using UnityEngine;
using System.Collections;

public class AnimControll : MonoBehaviour {

    public Animator anim;

    public bool idle;

    // Use this for initialization
    void Start () {
        anim = GetComponent<Animator>();
        anim.SetBool("idle", true);
    }
	
	// Update is called once per frame
	void Update () {
	    
	}
}
